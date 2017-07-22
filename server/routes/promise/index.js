let router = require('express').Router();
let fs = require('fs');
let util = require("util");

let mime = require("mime");
/**
 * :::::: 사용자가 가장 최근에 등록한 약속을 기준으로 조회
 */
router.route('/:uid').get(function (req, res) {
    let promiseModel = req.app.get('database').promise;
    let userModel = req.app.get('database').user;
    let uid = req.params.uid;
    console.log(uid);
    promiseModel.find({
        "uid": uid
    }, function (err, docs) {
        if (err || typeof docs[0] === "undefined") {
            res.status(400).end();
        } else {
            let category = docs[0].category;
            let date = docs[0].date;
            promiseModel.find({
                "category": category,
                "date" : date,
                "uid": {
                    $ne: uid
                }
            }, function (err, _docs) {
                if (err) {
                    res.status(500).end();
                } else {
                    res.status(200);
                    new Promise(function (resolve, reject) {

                        let promises = [];
                        for (var i = 0; i < _docs.length; i++) {
                            promises.push(new Promise(function (_resolve, _reject) {
                                userModel.getInfoFromUid(_docs[i].uid, _resolve);
                            }));
                        }
                        Promise.all(promises).then(function (results) {
                            let response = [];
                            let location = {};
                            location.lat = deg2rad(docs[0].location.lat);
                            location.lon = deg2rad(docs[0].location.lon);
                            for (var i = 0; i < results.length; i++) {
                                response.push({
                                    "nickname": results[i].nickname,
                                    "age": results[i].age,
                                    "distance": getDistance(location, _docs[i].location),
                                    "plan": _docs[i].plan,
                                    "date": _docs[i].date,
                                    "profileImage" : imageBase64Encode(results[i].picture)
                                });
                            }
                            resolve(response);
                        }).catch(function (err) {
                            console.log(err)
                        });

                    }).then(function (response) {
                        res.json(response);
                    }).catch(function () {});
                }
            });
        }
    }).sort({
        "createDate": -1
    });

});

/**
 * :::::: 새로운 약속 등록
 * category,
 * uid,
 * startDate,
 * endDate,
 * location,
 * plan
 */
router.route('/').post(function (req, res) {

    let promiseModel = req.app.get('database').promise;
    let category = req.body.category;
    let uid = req.body.uid;
    let date = req.body.date;
    let location = req.body.location;
    let plan = req.body.plan;


    new promiseModel({
        "category": category,
        "uid": uid,
        "date": date,
        "location": location,
        "plan": plan,
    }).save(function (err, promise) {
        if (err) {
            res.status(400).end();
        } else {
            res.status(201).end();
        }
    });
});

function getDistance(a, b) {

    b.lon = deg2rad(b.lon);
    b.lat = deg2rad(b.lat);

    let dlon = b.lon - a.lon;
    let dlat = b.lat - a.lat;

    let x = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(a.lat) * Math.cos(b.lat) * Math.pow(Math.sin(dlon / 2), 2);
    let y = 2 * Math.atan2(Math.sqrt(x), Math.sqrt(1 - x));

    return 6373 * y;

}

function deg2rad(deg) {
    return deg * Math.PI / 180;
}

function imageBase64Encode(src){
    var data = fs.readFileSync(src).toString("base64");
    var dataUri = util.format("data:%s;base64,%s", mime.lookup(src), data);
    return dataUri;
}
module.exports = router;


/*
 *_______
||       |
|| 던*짐 |
||_______|
||
⊂_ヽ 
|| ＼＼  Λ＿Λ 
||　 ＼ ('ㅅ')  두둠칫 
||　　 >　⌒ヽ 
* 　　/ 　 へ＼ 
　　 /　　/　＼＼ 
　　 ﾚ　ノ　　 ヽ_つ 
　　/　/ 두둠칫 
　 /　/| 
　(　(ヽ 
　|　|、＼ 
　| 丿 ＼ ⌒) 
　| |　　) / 
(`ノ )　　Lﾉ */