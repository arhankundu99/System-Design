const { Client } = require('@elastic/elasticsearch')

const elasticCloudID = process.env.ELASTIC_CLOUD_ID;
const elasticUsername = process.env.ELASTIC_USERNAME;
const elasticPassword = process.env.ELASTIC_PASSWORD;

const client = new Client({
  cloud: {
    id: elasticCloudID,
  },
  auth: {
    username: elasticUsername,
    password: elasticPassword
  }
});

module.exports = client;