let router = require('express').Router();

/**
 * :::::: 사용자가 가장 최근에 등록한 약속을 기준으로 조회
 */
router.route('/').get(function (req, res) {

});

/**
 * :::::: 새로운 약속 등록
 * uid,
 * startDate,
 * endDate,
 * location,
 * plan
 * 
 * ---- 서버에서 추가 ----
 * 생성 시간
 */
router.route('/').post(function (req, res) {
    let promiseModel = req.app.get('database').promise;
    let uid = req.body.uid;
    let startDate = req.body.startDate;
    let endDate = req.body.endDate;
    let location = req.body.location;
    let plan = req.body.plan;

    new promiseModel({
        "uid": uid,
        "startDate": startDate,
        "endDate": endDate,
        "location": location,
        "plan": plan
    }).save(function (err) {
        if (err) {
            res.status(400).end();
            return;
        }
        res.status(201).end();
    });
});