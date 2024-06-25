import { $authHost } from "./index";

export const createOrUpdateReview = async (userId, deviceId, rating, comment) => {
    const { data } = await $authHost.post('api/reviews', {
        userId,
        deviceId,
        rating,
        comment
    });
    return data;
};

export const fetchReviewsByDeviceId = async (deviceId) => {
    const { data } = await $authHost.get(`api/reviews/${deviceId}`);
    return data;
};
