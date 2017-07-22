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
    

    let userModel = req.app.get("database").user;
    let uid = req.body.uid;
    let email = req.body.email;
    let nickname = req.body.nickname;
    let picture = "profiles/" + uid + ".jpg";
    let age = req.body.age;
    let detail = req.body.detail;
    
    let sampleFile = req.files.sampleFile;
    sampleFile.mv(picture);


    new userModel({
        "uid": uid,
        "email": email,
        "nickname": nickname,
        "picture": picture,
        "age": age,
        "detail": detail
    }).save(function (err) {
        if (err) {
            res.status(400).end();
        }
        else {
            res.status(200).end();
        }
    });

});
router.route('/info').put(function (req, res) {

});
router.route('/info/:userId').get(function (req, res) {
    let userModel = req.app.get("database").user;
    userModel.findOne({"uid" : req.params.userId},function (err,doc){
        if(err){
            res.status(400).end();   
        }
        else{
            res.status(200).json(doc);
        }
    });
});


module.exports = router;