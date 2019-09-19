/**
 * Application REST endpoints handler
 */
const router = require('express').Router();

/**
 *  REST endpoints
 */
router.route('/')
    .post(addRating)

router.route('/all/:accommodationId')
    .get(getReviews);

router.route('/published/:accommodationId')
    .get(getPublishedReviews);

router.route('/unpublished')
    .get(getUnpublishedReviews);

router.route('/average')
    .get(findAverageRatingForAllAccommodations);

router.route('/average/:accommodationId')
    .get(findAverageAccommodationRating);

router.route('/publish/:reservationId')
    .put(publishComment);

router.route('/user/:username')
    .get(getUserComments);

/**
 * Knex MySQL query builder
 */
const knex = require('./knex');

/**
 *  Constructor for Reservation objects
 */
const Reservation = require('./reservation');

/**
 * Table in database to work with
 */
const reservationTableName = 'reservation';

/**
 *  Adding rating and comment for accommodation by user
 */
function addRating(request, response) {

    const reservation = new Reservation(request.body);

    if (!reservation.id) {
        response.status(400).send('Error! Reservation id has to be set.');
        return;
    }

    knex(reservationTableName)
        .insert(reservation)
        .then(() => { response.status(200).send(reservation); })
        .catch(() => { response.status(400).send('Error! Rating could not be added.'); });
}

/**
 * Retrieve all unpublished comments which belong to the given accommodation
 */
function getReviews(request, response) {
    knex(reservationTableName)
        .select('username', 'rating', 'comment', 'timestamp', 'published')
        .where('accommodationId', request.params.accommodationId)
        .then((results) => {
            results.map(result => result.published = result.published[0] === 1 ? true : false)
            response.status(200).send(results);
        })
        .catch(() => { response.status(400).send(); });
}

/**
 * Retrieve all published comments which belong to the given accommodation
 */
function getPublishedReviews(request, response) {
    knex(reservationTableName)
        .select('username', 'rating', 'comment', 'timestamp')
        .where('accommodationId', request.params.accommodationId)
        .andWhere('published', 1)
        .then((results) => { response.status(200).send(results); })
        .catch(() => { response.status(400).send(); });
}

/**
 * Retrieve all unpublished comments
 */
function getUnpublishedReviews(request, response) {
    knex(reservationTableName)
        .select('id', 'username', 'rating', 'comment', 'timestamp')
        .where('published', 0)
        .then((results) => { response.status(200).send(results); })
        .catch(() => { response.status(400).send(); });
}

/**
 * Find average rating for all accommodations
 */
function findAverageRatingForAllAccommodations(request, response) {
    knex(reservationTableName)
        .groupBy('accommodationId')
        .select('accommodationId')
        .avg('rating as averageRating')
        .then((results) => { response.status(200).send(results); })
        .catch(() => { response.status(400).send(); });
}

/**
 * Find average rating for the given accommodation
 */
function findAverageAccommodationRating(request, response) {
    knex(reservationTableName)
        .where('accommodationId', request.params.accommodationId)
        .groupBy('accommodationId')
        .select('accommodationId')
        .avg('rating as averageRating')
        .then((results) => { response.status(200).send(results); })
        .catch(() => { response.status(400).send(); });
}

/**
 *  Publish comment for the given reservation
 */
function publishComment(request, response) {

    knex(reservationTableName)
        .where('id', request.params.reservationId)
        .update('published', 1)
        .then(() => { response.status(200).send(); })
        .catch(() => { response.status(400).send(); });
}

/**
 *  Get all user comments for the given username
 */
function getUserComments(request, response) {

    knex(reservationTableName)
        .select('id', 'rating', 'comment', 'timestamp', 'published')
        .where('username', request.params.username)
        .then((results) => {
            results.map(result => result.published = result.published[0] === 1 ? true : false)
            response.status(200).send(results);
        })
        .catch(() => { response.status(400).send(); });
}

module.exports = router;