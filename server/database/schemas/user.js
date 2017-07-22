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
    "uid": { type: String },
    "email": { type: String },
    "nickname": { type: String },
    "picture": { type: String },
    "detail": { type: String }

}, {
        collection: "user"
    });

module.exports = schema;