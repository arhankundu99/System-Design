const axios = require('axios');

class Logger{
    #logstashHost;
    #id;
    constructor(id, logstashHost = null){
        this.#id = id
        this.#logstashHost = logstashHost
    }

    async sendLogToLogstash(logMessage){
        try{
            await axios({
                method: 'get',
                url: this.#logstashHost,
                data: logMessage,
            })
        }
        catch(error){
            console.error(error);
        }
    }
    
    async log(serviceName, message){
        const severity = "LOG";
        const timestamp = Date.now();
        const functionName = (new Error().stack.split("at ")[1]).trim();
        
        const logMessage = `${severity} ${timestamp} ${serviceName} ${this.#id} ${functionName} ${message}`;

        if(this.#logstashHost){
            try{
                await this.sendLogToLogstash(logMessage);
            }
            catch(e){
                console.error(e);
            }
        }

        console.log(logMessage)
        
    }

    async error(serviceName, message){
        const severity = "ERROR";
        const timestamp = Date.now();
        const functionName = new Error().stack.split('\n')[2].trim().split(' ')[1];
        
        const logMessage = `${severity} ${timestamp} ${serviceName} ${this.#id} ${functionName} ${message}`;

        // ERROR 2023-03-22T13:45:30Z search_service 123 sendSearchResults Unable to send search results to Kafka
        if(this.#logstashHost){
            try{
                await this.sendLogToLogstash(logMessage);
            }
            catch(e){
                console.error(e);
            }
        }

        console.error(logMessage)
        
    }

    async info(serviceName, message){
        const severity = "INFO";
        const timestamp = Date.now();
        const functionName = new Error().stack.split('\n')[2].trim().split(' ')[1];
        
        const logMessage = `${severity} ${timestamp} ${serviceName} ${this.#id} ${functionName} ${message}`;

        if(this.#logstashHost){
            try{
                await this.sendLogToLogstash(logMessage);
            }
            catch(e){
                console.error(e);
            }
        }

        console.info(logMessage)
        
    }
}

module.exports = Logger;