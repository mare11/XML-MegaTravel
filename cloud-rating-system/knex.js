const Knex = require('knex');

module.exports = {
    /**
    * Function for creating knex query builder for MySQL database provided by Google Cloud SQL
    */
    getKnexConnection: function getKnexConnection() {

        const config = {
            host: process.env.SQL_HOST || '35.240.120.23',
            user: process.env.SQL_USER || 'megatravel_user',
            password: process.env.SQL_PASSWORD || '12345',
            database: process.env.SQL_DATABASE || 'rating_system'
        };

        if (process.env.INSTANCE_CONNECTION_NAME && process.env.NODE_ENV === 'production') {
            config.socketPath = `/cloudsql/${process.env.INSTANCE_CONNECTION_NAME}`;
        }

        // Connect to the database
        const knex = Knex({
            client: 'mysql',
            connection: config
        });

        return knex;
    }
}