import jsSHA from 'jssha'

let util = {
    timeStampToLocalTime(timestamp) {
        let date = new Date(timestamp)
        return date.toLocaleString()
    },
    getDuration(time) {
        return Math.floor(time/3600) + ":" + Math.floor((time%3600) / 60) +":" +(time%60)
    },
    checkAllChinese(str) {
        let reg = /^[\u4E00-\u9FA5]*$/g;
        return reg.test(str);
    },
    checkAllChineseAndEnglishAndNumber(str) {
        let reg = /^[\u4E00-\u9FA5A-Za-z0-9]*$/g;
        return reg.test(str);
    },
    checkCharAndNumber(str) {
        let reg = /^[a-zA-Z0-9]*$/g;
        return reg.test(str);
    },
    checkCharAndNumberAndUnderscore(str) {
        let reg = /^[a-zA-Z0-9_]*$/g;
        return reg.test(str);
    },
    SHA512(str) {
        const shaObj = new jsSHA("SHA-512","TEXT",{encoding:"UTF8"})
        shaObj.update(str)
        return shaObj.getHash('HEX')
    },
    generateMonthArray(start,end) {
        let sd = new Date(start);
        let year = sd.getUTCFullYear();
        let month = sd.getMonth() + 1;
        let ed = new Date(end);
        let endYear = ed.getUTCFullYear();
        let endMonth = ed.getMonth() + 1;
        let nextMonth = (year,month) => {
            if(month === 12) {
                return {year:year+1,month:1}
            }else{
                return {year:year,month:month+1}
            }
        }
        let arr = []
        arr.push({year,month})
        console.log(year,month)
        while(year!==endYear||month!==endMonth){
            let next = nextMonth(year,month)
            year = next.year
            month = next.month
            arr.push({year,month})
        }
        return arr;
    },
    generateDayArray(start,end) {
        let sd = new Date(start);
        let year = sd.getUTCFullYear();
        let month = sd.getMonth() + 1;
        let day = sd.getDate();
        let ed = new Date(end);
        let endYear = ed.getUTCFullYear();
        let endMonth = ed.getMonth() + 1;
        let endDay = ed.getDate();
        let nextDay = (year,month,day) => {
            if(day === new Date(year,month - 1,0).getDate()) {
                if(month === 12) {
                    return {year:year+1,month:1,day:1}
                }else{
                    return {year:year,month:month+1,day:1}
                }
            }else{
                return {year:year,month:month,day:day+1}
            }
        }
        let arr = []
        arr.push({year,month,day})
        console.log(year,month,day)
        while(year!==endYear||month!==endMonth||day!==endDay){
            let next = nextDay(year,month,day)
            year = next.year
            month = next.month
            day = next.day
            arr.push({year,month,day})
        }
        return arr;
    }
}
export default util