from datetime import datetime

from etl.domain.enums import ETablesAvailable
from etl.domain.entities import InfoSpecEntity
from etl.domain.entities import InfoSchemaEntity
from etl.domain.entities import InfoStagesEntity
from etl.domain.entities import ContactsETLEntity

from etl.domain.ports import IMiddlewareTree
from etl.domain.repositories import IRepository

from tests.mocks import test_data_raw_sales


class ETLExtractSPY(IMiddlewareTree):
    def __init__(self, repo: IRepository):
        IMiddlewareTree.__init__(self)
        self.repo = repo
        self.args = None
        self.kwargs = None
        self.contact = None
        self.counter = 0

    def handle(self, contact: ContactsETLEntity, *args, **kwargs) -> ContactsETLEntity:
        self.counter += 1
        self.contact = contact
        self.args = args
        self.kwargs = kwargs

        extract_at = datetime.fromisoformat("2023-03-20T09:29:58.839423")
        spec = InfoSpecEntity(**{"columns": "test", "where": {"start": "2023-01-01", "end": "2023-01-02"}})
        schema = InfoSchemaEntity(name="test", database="test", table=ETablesAvailable.V_ZETTI_VENDAS)
        progress = [
            InfoStagesEntity(**{"stage": "PREPARE", "status": "STARTED", "updated_at": "2023-03-20T09:29:58.839490"}),
            InfoStagesEntity(**{"stage": "PREPARE", "status": "FINISHED", "updated_at": "2023-03-20T09:29:58.839608"}),
            InfoStagesEntity(**{"stage": "STREAM", "status": "STARTED", "updated_at": "2023-03-20T10:25:48.656502"}),
            InfoStagesEntity(**{"stage": "EXTRACT", "status": "STARTED", "updated_at": "2023-03-20T10:25:53.438842"}),
        ]

        data = test_data_raw_sales()

        return ContactsETLEntity(
            schema=schema,
            spec=spec,
            data=data,
            progress=progress,
            extract_at=extract_at
        )
