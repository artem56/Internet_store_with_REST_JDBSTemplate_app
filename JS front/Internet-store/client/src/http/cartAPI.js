import { $authHost } from "./index";

export const addToCart = async (userId, deviceId, quantity) => {
    const { data } = await $authHost.post('api/cart/add', null, {
        params: {
            userId: userId,
            deviceId: deviceId,
            quantity: quantity
        }
    });
    return data;
}

export const getCartItems = async (userId) => {
    const { data } = await $authHost.get('api/cart/items', {
        params: {
            userId: userId
        }
    });
    return data;
}

export const removeFromCart = async (userId, deviceId) => {
    const { data } = await $authHost.delete('api/cart/remove', {
        params: {
            userId: userId,
            deviceId: deviceId
        }
    });
    return data;
}

export const updateCartItem = async (userId, deviceId, quantity) => {
    const { data } = await $authHost.put('api/cart/update', null, {
        params: {
            userId: userId,
            deviceId: deviceId,
            quantity: quantity
        }
    });
    return data;
}

export const createOrder = async (userId, cartItems) => {
    const { data } = await $authHost.post('api/order/create', {
        userId: userId,
        items: cartItems
    });
    return data;
}
export const clearCart = async (userId) => {
    const { data } = await $authHost.post('api/cart/clear', null, {
        params: { userId }
    });
    return data;
};
