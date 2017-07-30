/**
 * ::::::::: USER :::::::::
 * 
 *      UID, 
 *      email,
 *      nickname, 
 *      picture,
 *      detail
 */

let mongoose = require('mongoose');
let schema = mongoose.Schema({
    "uid": { type: String, required: true, unique: true },
    "email": { type: String, required: true },
    "nickname": { type: String, required: true },
    "picture": { type: String, required: true },
    "age" : { type: Number, required: true },
    "detail": { type: String, required: true }

}, {
        collection: "user"
    });

module.exports = schema;