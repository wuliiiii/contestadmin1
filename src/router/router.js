import {createRouter,createWebHashHistory ,createWebHistory} from 'vue-router'
const routes = [
    {
        path:"/",
        component:()=>import("../page/base.vue"),
        redirect:"/index",
        children:[
            {
                path:"/index",
                component:()=>import('../page/index.vue')
            },
            {
                path:"/stuList",
                component:()=>import('../page/stuList.vue')
            },
            {
                path:"/versionInfo",
                component:()=>import('../components/Version/content.vue')
            },
            {
                path: "/user/profile",
                component: () => import('../page/user/profile.vue')
            },
            {
                path: "/codeforces/list",
                component: () => import("../page/codeforces/list.vue")
            },
            {
                path: "/codeforces/rank",
                component: () => import("../page/codeforces/userRank.vue")
            },
            {
                path: "/codeforces/contestInfo/:id",
                component: ()=> import("../page/codeforces/ContestInfo.vue")
            },
            {
                path: "/atcoder/list",
                component: () => import("../page/atcoder/list.vue")
            },
            {
                path: "/atcoder/rank",
                component: () => import("../page/atcoder/userRank.vue")
            },
            {
                path: "/atcoder/contestInfo/:id",
                component: ()=> import("../page/atcoder/ContestInfo.vue")
            },
            {
                path: "/codeforces/account/:name",
                component: () => import("../page/codeforces/AccountInformation.vue")
            },
            {
                path: "/atcoder/account/:name",
                component: () => import("../page/atcoder/AccountInformation.vue")
            },
            {
                path: "/user/myTrainInfo",
                component: () => import("../page/user/TrainInformation.vue")
            },
            {
                path: "/user/trainInfo/:username",
                component: () => import("../page/user/TrainInformation.vue")
            },
            // {
            //     path: "/graph/tags",
            //     component: () => import("../page/graph/tags.vue")
            // },
            // {
            //     path: "/graph/tag/:tid",
            //     component: () => import("../page/graph/tag.vue")
            // },
            // {
            //     path: "/graph/RatingChange/Codeforces",
            //     component: () => import("../page/graph/CodeforcesRatingChange.vue")
            // },
            // {
            //     path: "/graph/RatingChange/Atcoder",
            //     component: () => import("../page/graph/AtcoderRatingChange.vue")
            // },
            // {
            //     path: "/monthlyRating",
            //     component: () => import("../page/MonthlyRating.vue")
            // },
            // {
            //     path: "/graph/GradeCompare",
            //     component: () => import("../page/graph/GradeCompare.vue")
            // },
            // {
            //     path: "/graph/GradeCompare/:school",
            //     component:() => import("../page/graph/GradeCompare.vue")
            // },
            {
                path: "/codeforces/problemList",
                component: () => import("../page/codeforces/problemList.vue")
            },
            // {
            //     path: "/graph/YearCompare/:school",
            //     component: () => import("../page/graph/YearCompare.vue")
            // },
            // {
            //     path: "/graph/YearCompare/",
            //     component: () => import("../page/graph/YearCompare.vue")
            // },
            // {
            //     path: "/graph/UserCount/",
            //     component: () => import("../page/graph/UserCount.vue")
            // },
            // {
            //     path: "/graph/SolveCompare/",
            //     component: () => import("../page/graph/CodeforcesOKSubmitCompare.vue")
            // },
            // {
            //     path: "/intelligent_training/list",
            //     component: () => import("../page/intelligent_training/trainingList.vue")
            // },
            // {
            //     path: "/intelligent_training/contest/rank/:id",
            //     component: () => import("../page/intelligent_training/trainingRank.vue")
            // },
            // {
            //     path: "/intelligent_training/contest/submit/:id",
            //     component: () => import("../page/intelligent_training/trainingSubmit.vue")
            // },
            // {
            //     path: "/intelligent_training/generateSuggest",
            //     component: () => import("../page/intelligent_training/GenerateSuggest.vue")
            // },
            // {
            //     path: "/intelligent_training/contest/:id/problem",
            //     component: () => import("../page/intelligent_training/userTrainingProblemList.vue")
            // },
            // {
            //     path: "/user/submitApplication",
            //     component: () => import("../page/user/SubmitApplication.vue")
            // },
            // {
            //     path: "/graph/RecentContestParticipate",
            //     component: () => import("../page/graph/RecentContestParticipate.vue")
            // }

        ]
    },
    {
        path: "/login",
        component:()=>import("../page/login.vue"),
    },
    {
        path: "/register",
        component:()=>import("../page/register.vue")
    }

]

const router = createRouter({
    history: createWebHistory("/littlebuctoj/"),
    routes,
})

export default router