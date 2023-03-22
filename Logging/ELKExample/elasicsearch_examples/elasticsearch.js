require('dotenv').config()
const client = require("./elasticClient");

client.info()
  .then(response => console.log(response))
  .catch(error => console.error(error))


async function addData(index, data) {
  try {
    await client.index({
      index,
      body: data
    });
    console.log("Successfully added data.");
  }
  catch(e){
    console.error(e);
  }
}

async function deleteIndex(index) {
  try {
    const { body } = await client.indices.delete({
      index
    });
    console.log(`Index '${index}' deleted: ${body.acknowledged}`);
  } catch (error) {
    return error;
  }
}

async function readIndex(index) {
  try {
    const body = await client.search({
      index
    })
    return body.hits.hits;
  }
  catch (e) {
    console.error(e);
    return null;
  }
}

deleteIndex("game-of-thrones").catch(error => console.error(error))
