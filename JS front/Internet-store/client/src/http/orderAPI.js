import { $authHost } from "./index";

export const fetchOrders = async (userId) => {
    const { data } = await $authHost.get('api/order', {
        params: {
            userId: userId
        }
    });
    return data;
};

export const createOrUpdateReview = async (userId, deviceId, rating, comment) => {
    const { data } = await $authHost.post('api/reviews', {
        userId: userId,
        deviceId: deviceId,
        rating: rating,
        comment: comment
    });
    return data;
};