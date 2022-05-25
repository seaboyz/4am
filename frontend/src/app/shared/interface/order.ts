import { Address } from "./address"
import { OrderItem } from "./order-item"

export interface Order
{
    customerId: number
    shippingAddress: Address
    orderItems: OrderItem[]
}
