/**
 * Node.js express application object
 */
const express = require('express');
const bodyParser = require('body-parser');
const application = express();
application.enable('trust proxy');
application.use(bodyParser.json())

/**
 * Retireve router
 */
const router = require('./endpoints');
application.use('/api/reviews', router);

/**
 * Port on which application listens
 */
const port = process.env.PORT || 8020;

//Set up on which port application should listen for requests
application.listen(port, () => {
    console.log(`App listening on port ${port}`);
    console.log('Press Ctrl+C to quit.');
});