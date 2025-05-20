from abc import ABC

from ..ports import IPaginationSpecification


class PaginationSpecification(IPaginationSpecification, ABC):

    def pagination(self, page: int, per_page: int):
        self.per_page = per_page
        self.offset = (page - 1) * per_page
        return self

    def to_query(self) -> str:
        return " LIMIT {} OFFSET {}".format(self.per_page, self.offset)

    def to_document(self) -> dict:
        return {"skip": self.offset, "limit": self.per_page}
