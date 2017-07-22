var router = require("express").Router();
var fs = require("fs");
const fileUpload = require('express-fileupload');


/**
 * ::::::::: USER :::::::::
 * 
 *      UID, 
 *      email,
 *      nickname, 
 *      picture,
 *      detail
 */


router.route('/info').post(function (req, res) {

    let sampleFile = req.files.sampleFile;
    sampleFile.mv('./profiles/' + req.body.uid + '.jpg');


    let userModel = req.app.get("database").user;
    let uid = req.body.uid;
    let email = req.body.email;
    let nickname = req.body.nickname;
    let picture = "profiles/" + uid + ".jpg";
    let age = req.body.age;
    let detail = req.body.detail;


    new userModel({
        "uid": uid,
        "email": email,
        "nickname": nickname,
        "picture": picture,
        "age": age,
        "detail": detail
    }).save(function (err, user) {
        if (err) {
            res.status(400).end();
        }
        else {
            res.status(201).end();
        }
    });

});
router.route('/info').put(function (req, res) {

});
router.route('/info/:userId').get(function (req, res) {
    console.log(req.params.userId);
});


module.exports = router;