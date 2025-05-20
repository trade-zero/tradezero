import pytest

from etl.domain.enums.info_stages import EInfoStages
from etl.domain.enums.info_status import EInfoStatus
from etl.domain.enums.tables_available import ETablesAvailable
from etl.domain.entities.info_stages_entity import InfoStagesEntity

from etl.domain.entities.info_spec_entity import InfoSpecEntity
from etl.domain.entities.info_schema_entity import InfoSchemaEntity
from etl.domain.entities.contacts_etl_entity import ContactsETLEntity

from shared.finder_error import wrap_info_decorator
from shared.finder_error import class_decorator


info = wrap_info_decorator(
    on_init=InfoStagesEntity(stage=EInfoStages.PREPARE, status=EInfoStatus.STARTED),
    on_exit=InfoStagesEntity(stage=EInfoStages.PREPARE, status=EInfoStatus.FINISHED),
    on_except=InfoStagesEntity(stage=EInfoStages.PREPARE, status=EInfoStatus.ERROR),
    wrapper_exc=ZeroDivisionError,
)


@class_decorator(function_decorator=info, include=["div"])
class DecoratorOnInit:

    def __init__(self):
        pass

    def div(self, contact: ContactsETLEntity):
        return contact


def test_wrap_info_decorator():
    schema = InfoSchemaEntity(name="test", database="test", table=ETablesAvailable.V_ZETTI_VENDAS)
    spec = InfoSpecEntity()
    contact = ContactsETLEntity(schema=schema, spec=spec)

    obj = DecoratorOnInit()
    res = obj.div(contact=contact)
    assert contact == res


def test_wrap_info_decorator_with_div_zero():
    schema = InfoSchemaEntity(name="test", database="test", table=ETablesAvailable.V_ZETTI_VENDAS)
    spec = InfoSpecEntity()
    contact = ContactsETLEntity(schema=schema, spec=spec)
    obj = DecoratorOnInit()
    setattr(obj, "div", lambda contact: 1/0)

    with pytest.raises(ZeroDivisionError):
        obj.div(contact=contact)
