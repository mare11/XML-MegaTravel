function Reservation(reservation) {
    this.id = reservation.id;
    this.accommodationId = reservation.accommodationId;
    this.username = reservation.username;
    this.rating = reservation.rating;
    this.comment = reservation.comment;
    this.published = false;
    this.timestamp = new Date(Date.UTC(new Date().getFullYear(), new Date().getMonth(), new Date().getDate(),
        new Date().getHours(), new Date().getMinutes(), new Date().getSeconds(), new Date().getMilliseconds()));
}

module.exports = Reservation;
