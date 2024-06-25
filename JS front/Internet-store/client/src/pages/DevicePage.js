import React, { useEffect, useState, useContext } from 'react';
import { Image, Button, Modal, Form } from 'react-bootstrap';
import { useParams } from 'react-router-dom';
import ReactStars from "react-rating-stars-component";
import { fetchOneDevice } from '../http/deviceAPI';
import { addToCart } from '../http/cartAPI';
import { createOrUpdateReview, fetchReviewsByDeviceId } from '../http/reviewAPI';
import { Context } from '../index';

const DevicePage = () => {
    const [device, setDevice] = useState({ info: [] });
    const [reviews, setReviews] = useState([]);
    const [showReviewModal, setShowReviewModal] = useState(false);
    const [rating, setRating] = useState(0);
    const [comment, setComment] = useState('');
    const { id } = useParams();
    const { user } = useContext(Context);

    useEffect(() => {
        const fetchData = async () => {
            const deviceData = await fetchOneDevice(id);
            setDevice(deviceData);

            const reviewsData = await fetchReviewsByDeviceId(id);
            setReviews(reviewsData);

            console.log("Device rating:", deviceData.rating); // Debugging line
        };

        fetchData();
    }, [id]);

    const handleAddToCart = async () => {
        if (user.isAuth) {
            await addToCart(user.user.userId, device.id, 1); // Ensure user ID is correctly accessed
            alert("Устройство добавлено в корзину");
        } else {
            alert("Пожалуйста, войдите в систему, чтобы добавить устройство в корзину");
        }
    };

    const handleReviewSubmit = async () => {
        if (user.isAuth) {
            await createOrUpdateReview(user.user.userId, device.id, rating, comment); // Ensure user ID is correctly accessed
            setShowReviewModal(false);
            const updatedReviews = await fetchReviewsByDeviceId(id);
            setReviews(updatedReviews);
        } else {
            alert("Пожалуйста, войдите в систему, чтобы оставить отзыв");
        }
    };

    return (
        <div className="container mt-3">
            <div className="row">
                <div className="col-md-4 mt-2">
                    <Image height={220} weight={200} src={process.env.REACT_APP_API_URL + device.img} />
                </div>
                <div className="col-md-4">
                    <div className="d-flex flex-column align-items-center">
                        <h2>{device.name}</h2>
                        <div
                            className="d-flex align-items-center justify-content-center"
                            style={{
                                width: '100%',
                                height: 60,
                                fontSize: 64,
                                whiteSpace: 'nowrap'
                            }}
                        >
                            <ReactStars
                                key={`device-rating-${device.rating}`} // Add key to force re-render
                                count={5}
                                value={device.rating} // Use device rating
                                size={60} // Increased size
                                edit={false}
                                isHalf={true}
                                activeColor="#ffd700"
                            />
                        </div>
                    </div>
                </div>
                <div className="col-md-4">
                    <div
                        className="card d-flex flex-column align-items-center justify-content-around"
                        style={{ width: 300, height: 300, fontSize: 32, border: '5px solid lightgray' }}
                    >
                        <h3>{device.price} руб.</h3>
                        <Button variant={"outline-dark"} onClick={handleAddToCart}>
                            Добавить в корзину
                        </Button>
                    </div>
                </div>
            </div>
            <div className="row d-flex flex-column m-3">
                <h1>Характеристики</h1>
                {device.info.map((i, index) => (
                    <div
                        className="row"
                        key={i.id}
                        style={{ background: index % 2 === 0 ? 'lightgray' : 'transparent', padding: 10 }}
                    >
                        {i.title}: {i.description}
                    </div>
                ))}
            </div>
            <div className="row d-flex flex-column m-3">
                <h1>Отзывы</h1>
                {reviews.length > 0 ? (
                    reviews.map(review => (
                        <div key={review.id} className="mb-3">
                            <ReactStars
                                count={5}
                                value={review.rating}
                                size={30} // Increased size for reviews
                                edit={false}
                                isHalf={true}
                                activeColor="#ffd700"
                            />
                            <p>{review.comment}</p>
                        </div>
                    ))
                ) : (
                    <p>Отзывов пока нет</p>
                )}
                <Button variant="primary" onClick={() => setShowReviewModal(true)}>
                    Оставить отзыв
                </Button>
            </div>

            <Modal show={showReviewModal} onHide={() => setShowReviewModal(false)}>
                <Modal.Header closeButton>
                    <Modal.Title>Оставить отзыв</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group>
                            <Form.Label>Оценка</Form.Label>
                            <ReactStars
                                count={5}
                                value={rating}
                                onChange={newRating => setRating(newRating)}
                                size={30} // Increased size for review form
                                isHalf={true}
                                activeColor="#ffd700"
                            />
                        </Form.Group>
                        <Form.Group>
                            <Form.Label>Комментарий</Form.Label>
                            <Form.Control
                                as="textarea"
                                rows={3}
                                value={comment}
                                onChange={e => setComment(e.target.value)}
                            />
                        </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={() => setShowReviewModal(false)}>
                        Закрыть
                    </Button>
                    <Button variant="primary" onClick={handleReviewSubmit}>
                        Отправить
                    </Button>
                </Modal.Footer>
            </Modal>
        </div>
    );
}

export default DevicePage;
