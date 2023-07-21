import axios from 'axios'


const Axios = axios.create({
        baseURL: "/ACDC/api",
        headers: {
            'Content-Type': 'application/json;charset=UTF-8'
        },
    }
)
Axios.interceptors.request.use(async config => {
    console.log("request config:", config)
    if (!config.url.startsWith("/user")) {
        return config;
    }
    if (config.url=="/user/flush") {
        return config;
    }
    try {
        let token = window.localStorage.getItem("normal_token")
        if (token == null) {
            return config;
        }
        if (window.localStorage.getItem("normal_expire") - Date.now() <= 1000 * 1800) {
            await requestUtil.flushToken()
        }
        config.headers.token = token
        return config;
    } catch {
        return config;
    }
})
let codeforcesAPI = {
    getSingleCodeforcesContestInfo(cid) {
        return Axios.get(`/codeforces/contest/${cid}`)
    },
    getTopTenCodeforces() {
        return Axios.get("/codeforces/TopTen")
    },
    getThisUserCodeforcesAccount() {
        return Axios.get("/user/CodeforcesAccount")
    },
    setMainCodeforces(codeforcesAccountId) {
        return Axios.post("/user/codeforces/setMain", {codeforcesAccountId})
    },
    addNewCodeforcesAccount(codeforces_id, is_main) {
        return Axios.post("/user/CodeforcesAccount", {codeforces_id, is_main})
    },
    getCodeforcesContestList() {
        return Axios.get("/codeforces/list")
    },
    getAllCodeforcesAccount() {
        return Axios.get("/codeforces/rank")
    },
    getCodeforcesParticipateList(cid) {
        return Axios.get(`/codeforces/participateList/${cid}`)
    },
    getCodeforcesContestSubmit(cid) {
        return Axios.get(`/codeforces/submitList/${cid}`)
    },
    getCodeforcesSubmitCode(sid) {
        return Axios.get(`/codeforces/submitcode/${sid}`)
    },
    getCodeforcesUserParticipate(codeforces_id) {
        return Axios.get(`/codeforces/participate/${codeforces_id}`)
    },
    getCodeforcesUserAfterSolve(codeforces_id) {
        return Axios.get(`/codeforces/afterSubmit/${codeforces_id}`)
    },
    getCodeforcesUserContestSolve(codeforces_id) {
        return Axios.get(`/codeforces/contestSubmit/${codeforces_id}`)
    },
    getCodeforcesAccountParticipate(username) {
        return Axios.get(`/codeforces/user/participate/${username}`)
    },
    getCodeforcesAccountAfterSolve(username) {
        return Axios.get(`/codeforces/user/afterSubmit/${username}`)
    },
    getCodeforcesAccountContestSolve(username) {
        return Axios.get(`/codeforces/user/contestSubmit/${username}`)
    },
    getCodeforcesMainAccountRatingChange() {
        return Axios.get(`/codeforces/allUserMainAccountRatingChange`)
    },
    getCodeforcesRecentOneMonthInfo() {
        return Axios.get("/codeforces/recentOneMonthContest")
    },
    getCodeforcesProblemList(page, pageSize, sortProperty, sortDirection, search, startDifficulty, endDifficulty) {
        return Axios.get(`/codeforces/problemList?page=${page}&pageSize=${pageSize}&sortProperty=${sortProperty}&sortDirection=${sortDirection}&startDifficulty=${startDifficulty}&endDifficulty=${endDifficulty}&search=${search}`)
    },
    getProblemTags() {
        return Axios.get("/codeforces/getTagList")
    },
    getYearCompare(school) {
        return Axios.get("/codeforces/getYearCompare?school=" + school)
    },
    getTagCodeforcesOKSubmit(tid) {
        return Axios.get(`/tag/OKSubmitInfo/${tid}`)
    },
    getOKSubmitByUids(uids) {
        return Axios.get(`/codeforces/OKSubmitInfo/${uids}`)
    }
}
let atcoderAPI = {
    getSingleAtcoderContestInfo(cid) {
        return Axios.get(`/atcoder/contest/${cid}`)
    },
    getTopTenAtcoder() {
        return Axios.get("/atcoder/TopTen")
    },
    setMainAtcoder(atcoderAccountId) {
        return Axios.post("/user/codeforces/setMain", {atcoderAccountId})
    },
    getThisUserAtcoderAccount() {
        return Axios.get("/user/AtcoderAccount")
    },
    addNewAtcoderAccount(atcoder_id, is_main) {
        return Axios.post("/user/AtcoderAccount", {atcoder_id, is_main})
    },
    getAtcoderSubmitCode(sid) {
        return Axios.get(`/atcoder/submitcode/${sid}`)
    },
    getAtcoderContestSubmit(cid) {
        return Axios.get(`/atcoder/submitList/${cid}`)
    },
    getAtcoderParticipateList(cid) {
        return Axios.get(`/atcoder/participateList/${cid}`)
    },
    getAllAtcoderAccount() {
        return Axios.get("/atcoder/rank")
    },
    getAtcoderContestList() {
        return Axios.get("/atcoder/list")
    },
    getAtcoderUserParticipate(atcoder_id) {
        return Axios.get(`/atcoder/participate/${atcoder_id}`)
    },
    getAtcoderUserAfterSolve(atcoder_id) {
        return Axios.get(`/atcoder/afterSubmit/${atcoder_id}`)
    },
    getAtcoderUserContestSolve(atcoder_id) {
        return Axios.get(`/atcoder/contestSubmit/${atcoder_id}`)
    },
    getAtcoderAccountParticipate(username) {
        return Axios.get(`/atcoder/user/participate/${username}`)
    },
    getAtcoderAccountAfterSolve(username) {
        return Axios.get(`/atcoder/user/afterSubmit/${username}`)
    },
    getAtcoderAccountContestSolve(username) {
        return Axios.get(`/atcoder/user/contestSubmit/${username}`)
    },
    getAtcoderMainAccountRatingChange() {
        return Axios.get("/atcoder/allUserMainAccountRatingChange")
    },
    getAtcoderRecentOneMonthInfo() {
        return Axios.get("/atcoder/recentOneMonthContest")
    },
}
let intelligentAPI = {
    getContestList() {
        return Axios.get("/intelligentTrain")
    },
    getUserQuestionList(id) {
        return Axios.get(`/intelligentTrain/getUserQuestionList/${id}`)
    },
    getUserQuestionListDetail(uid, tid) {
        return Axios.get(`/intelligentTrain/getUserQuestionListDetail/${uid}/${tid}`)
    },
    joinTraining(tid) {
        return Axios.post(`/user/intelligentTrain/addUserQuestionList/${tid}`)
    },
    getFuncs() {
        return Axios.get("/intelligentTrain/funcs")
    },
    useFunc(funcName,num){
        return Axios.get(`/user/intelligentTrain/func/${funcName}/${num}`)
    },
    getContestInfo(cid){
        return Axios.get(`/intelligentTrain/contestInfo/${cid}`)
    },
    getIntelligentTrainContestSubmitInfo(cid){
        return Axios.get(`/intelligentTrain/contestSubmitInfo/${cid}`)
    }
}
let requestUtil = {
    login(username, password) {
        return Axios.post("/login", {username, password})
    },
    async flushToken() {
        let token = window.localStorage.getItem("normal_token")
        if (token === null || token === "") {
            return false
        }
        return await Axios.post("/user/flush", {}, {headers: {token}}).then(res => {
            if (res.data.code === 200) {
                let result = res.data.result
                window.localStorage.setItem("normal_token", result.token)
                window.localStorage.setItem("normal_expire", (Date.now() + 1000 * 3600).toString())
                return true
            } else {
                return false
            }
        })
    },
    getSchoolList() {
        return Axios.get("/school")
    },
    register(username, password, school, classname, year, stuno, realname) {
        return Axios.post("/register", {username, password, school, classname, year, stuno, realname})
    },
    resetPassword(oldPassword, newPassword) {
        return Axios.post("/user/resetPassword", {oldPassword, newPassword})
    },
    getMyApplication() {
        return Axios.get("/user/application",)
    },
    getMyInfo() {
        return Axios.get("/user/getInfo")
    },
    updateMyInfo(classname, year, stuno, realname) {
        return Axios.post("/user/modifyInfo", {classname, year, stuno, realname})
    },
    getAllUser() {
        return Axios.get("/alluser")
    },
    getRecentSubmit() {
        return Axios.get("/submit/week")
    },
    getFutureContest() {
        return Axios.get("/futureContest")
    },
    getTagList() {
        return Axios.get("/tag/list")
    },
    getTagUserInfo(tid) {
        return Axios.get(`/tag/userInfo/${tid}`)
    },
    getTagContestInfo(tid) {
        return Axios.get(`/tag/stucontestInfo/${tid}`)
    },
    getTagMonthSubmit(tid) {
        return Axios.get(`/tag/submitInfo/${tid}`)
    },
    getSpanMonthlyRating(startTime, endTime) {
        return Axios.get(`/MonthlyRating/${startTime}/${endTime}`)
    },
    getTagSpanMonthlyRating(tid, startTime, endTime) {
        return Axios.get(`/TagMonthlyRating/${tid}/${startTime}/${endTime}`)
    },
    getAllversionContent() {
        return Axios.get(`/version/getAllVersion`)
    },
    SubmitParserApplication(operation, params) {
        return Axios.post("/user/application",{operation, params})
    },
    codeforcesAPI,
    atcoderAPI,
    intelligentAPI

}

export default requestUtil