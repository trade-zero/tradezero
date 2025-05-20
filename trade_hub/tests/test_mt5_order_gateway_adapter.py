from router.infrastructure.adapters.mt5_order_gateway_adapters import Mt5GatewayAdapter


def test_get_order_pending():
    gateway = Mt5GatewayAdapter()
    gateway.connect()
    gateway.get_pending_orders("WIN" + "M23")
