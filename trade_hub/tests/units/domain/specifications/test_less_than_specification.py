from shared.domain.specifications import LessThanSpecification


def test_less_than_specification():
    # Arrange
    users = [
        {"user_id": 0, "user_name": "Jorge"},
        {"user_id": 1, "user_name": "Maria"},
        {"user_id": 2, "user_name": "John"},
    ]
    field = "user_id"
    value = 1

    # Act
    spec = LessThanSpecification(field, value)
    user_list = [*filter(lambda doc: spec.is_satisfied_by(doc.get("user_id", None)), users)]

    # Assert
    assert len(user_list) == 1
    assert spec.to_query() == f" {field} < {value} "
    assert spec.to_document() == {field: {"$lt": field}}
