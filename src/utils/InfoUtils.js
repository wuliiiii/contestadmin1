import api from "./axiosUtil";


const InfoUtils = {
    async getSchoolMap() {
        var schoolMap = {};
        var schoolList = window.localStorage.getItem("schoolList")
        let schooListExpire = window.localStorage.getItem("schoolListExpire")
        if (schoolList == null || parseInt(schooListExpire) < Date.now()) {
            await api.getSchoolList().then(res => {
                if (res.data.code === 200) {
                    window.localStorage.setItem("schoolList", JSON.stringify(res.data.result))
                    window.localStorage.setItem("schoolListExpire", Date.now() + 1000 * 60 * 60 )
                    schoolList = res.data.result
                    schoolList.forEach(element => {
                        schoolMap[element.id] = element.name
                    });
                }else{
                    console.log(res)
                }
            })
        } else {
            schoolList = JSON.parse(schoolList)
            schoolList.forEach(element => {
                schoolMap[element.id] = element.name
            });
        }
        return {schoolMap,schoolList}
    },
    // 检查用户已登录
    isLogin(){
        return window.localStorage.getItem("normal_token") != null && parseInt(window.localStorage.getItem("normal_expire")) >= Date.now()
    },
    // 该方法用于获取当前登录用户的用户ID
    getUserId(){
        return window.localStorage.getItem("normal_id")
    }
}
export default InfoUtils;