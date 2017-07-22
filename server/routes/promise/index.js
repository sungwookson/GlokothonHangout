let router = require('express').Router();

/**
 * :::::: 사용자가 가장 최근에 등록한 약속을 기준으로 조회
 */
router.route('/:uid').get(function (req, res) {
    let promiseModel = req.app.get('database').promise;
    let userModel = req.app.get('database').user;
    let uid = req.params.uid;
    console.log(uid);
    promiseModel.find({"uid" : uid}, function(err, docs){
        if (err || typeof docs[0] === "undefined"){
            res.status(400).end();
        }
        else{
            let category = docs[0].category;
            promiseModel.find({"category" : category, "uid": { $ne: uid } }, function(err, _docs){
                if(err){
                    res.status(500).end();
                }
                else{
                    res.status(200);
                    new Promise(function(resolve,reject){
                        for(var i =0;i<_docs.length;i++){
                        _docs[i].age = userModel.getAgeFromUid(_docs[i].uid);
                    }
                    resolve();
                    }).then(function(){
                        res.json(_docs);
                    }).catch(function(){});
                }
            });
        }
    }).sort({"createDate" : -1});
    
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
    let startDate = req.body.startDate;
    let endDate = req.body.endDate;
    let location = req.body.location;
    let plan = req.body.plan;


    new promiseModel({
        "category": category,
        "uid": uid,
        "startDate": startDate,
        "endDate": endDate,
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