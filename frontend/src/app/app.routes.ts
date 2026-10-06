import { Routes } from '@angular/router';
import { PageNotFound } from './page-not-found/page-not-found';
import { Login } from './features/auth/login/login';
import { Register } from './features/auth/register/register';
import { Profile } from './features/profile/profile';
import { College } from './features/colleges/college/college';
import { AdminLayout } from './layouts/admin-layout/admin-layout';
import { adminGuard } from './core/guards/admin-guard';
import { StudentLayout } from './layouts/student-layout/student-layout';
import { studentGuard } from './core/guards/student-guard';
import { Landingpagecomponent } from './features/landingpagecomponent/landingpagecomponent';
import { Dashboard } from './features/dashboard/dashboard';
import { MyNetwork } from './features/connections/my-network/my-network';
import { RightSidebar } from './shared/right-sidebar/right-sidebar';
import { AdminPosts } from './admin/admin-posts/admin-posts';
import { AllStudents } from './features/connections/all-students/all-students';
import { Chat } from './features/chat/chat';
import { Notification } from './features/notification/notification';

export const routes: Routes = [
    {
        path: '',
        component: Landingpagecomponent,
        pathMatch: 'full'
    },
    {
        path: 'login',
        component: Login
    },
    {
        path: 'register',
        component: Register
    },
    {
        path: 'admin',
        component: AdminLayout,
        canActivate: [adminGuard],
        children: [
            {
                path: 'college',
                component: College
            },
            {
                path: 'profile',
                component: Profile
            },
            {
                path: 'dashboard',
                component: Dashboard
            },
            {
                path: 'posts',
                component: AdminPosts
            },
            {
                path: 'students',
                component: AllStudents
            },
            {
                path:'notification',
                component: Notification
            }
        ]
    },
    {
        path: '',
        component: StudentLayout,
        canActivate: [studentGuard],
        children: [
            {
                path: 'post',
                component: RightSidebar,
                pathMatch: 'full'
            },
            {
                path: 'profile',
                component: Profile
            },
            {
                path: 'mynetwork',
                component: MyNetwork
            },
            {
                path: 'chat',
                component: Chat
            },
            {
                path: 'chat/:userId',
                component: Chat
            },
            {
                path:'notification',
                component: Notification
            }
        ]
    },
    {
        path: '**',
        component: PageNotFound
    }
];
