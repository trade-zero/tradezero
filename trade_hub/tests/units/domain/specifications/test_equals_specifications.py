from shared.domain.specifications import EqualsSpecification


def test_equals_specifications():
    # Arrange
    users = [
        {"user_id": 0, "user_name": "Jorge"},
        {"user_id": 1, "user_name": "Maria"},
        {"user_id": 2, "user_name": "John"},
    ]
    field = "user_id"
    value = 1

    # Act
    equal = EqualsSpecification(field, value)
    user, *_ = [*filter(lambda doc: equal.is_satisfied_by(doc.get("user_id", None)), users)]

    # Assert
    assert user.get("user_id", None) == value
    assert equal.to_query() == f" {field} = {value} "
    assert equal.to_document() == {field: value}
