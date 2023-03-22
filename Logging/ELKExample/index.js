const Logger = require('./logger')

require('dotenv').config();

const logger = new Logger(Math.random() * 100000, process.env.LOGSTASH_HOST);

logger.info("my-service-12", "This service was able to fetch data");

