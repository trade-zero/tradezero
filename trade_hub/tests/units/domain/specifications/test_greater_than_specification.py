from shared.domain.specifications import GreaterThanSpecification


def test_greater_than_specification():
    # Arrange
    users = [
        {"user_id": 0, "user_name": "Jorge"},
        {"user_id": 1, "user_name": "Maria"},
        {"user_id": 2, "user_name": "John"},
    ]
    field = "user_id"
    value = 0

    # Act
    great_than = GreaterThanSpecification(field, value)
    user_list = [*filter(lambda doc: great_than.is_satisfied_by(doc.get("user_id", None)), users)]

    # Assert
    assert len(user_list) == 2
    assert great_than.to_query() == f" {field} > '{value}' "
    assert great_than.to_document() == {field: {"$gt": field}}
