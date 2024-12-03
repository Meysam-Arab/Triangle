-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Apr 08, 2023 at 10:33 AM
-- Server version: 5.7.27
-- PHP Version: 5.6.40

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `triii_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `feedback`
--

CREATE TABLE `feedback` (
  `feedback_id` bigint(20) NOT NULL,
  `feedback_guid` varchar(36) NOT NULL,
  `title` varchar(200) DEFAULT NULL,
  `description` text NOT NULL,
  `email` varchar(260) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `log_event`
--

CREATE TABLE `log_event` (
  `log_event_id` bigint(20) NOT NULL,
  `log_event_guid` varchar(36) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `controller_name_and_action_name` varchar(500) NOT NULL,
  `error_message` text NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `log_event`
--

INSERT INTO `log_event` (`log_event_id`, `log_event_guid`, `user_id`, `controller_name_and_action_name`, `error_message`, `created_at`, `updated_at`, `deleted_at`) VALUES
(1, '5bcf1deb2dcbe5.27976083', NULL, ' خطای طرف گوشی ::dalvik.system.PathClassLoader', 'Main Message: message: خطای کلی رخ داد :near \"WHERE\": syntax error (code 1): , while compiling: SELECT * FROM tasks WHERE task_id IN ( SELECT task_id FROM tasks WHERE task_start_date BETWEEN 13970801 AND 13970831 OR WHERE task_end_date BETWEEN 13970801 AND 13970831 ) ORDER BY task_start_time ASC#LocalizedMessage:#near \"WHERE\": syntax error (code 1): , while compiling: SELECT * FROM tasks WHERE task_id IN ( SELECT task_id FROM tasks WHERE task_start_date BETWEEN 13970801 AND 13970831 OR WHERE task_end_date BETWEEN 13970801 AND 13970831 ) ORDER BY task_start_time ASC#Cause:#ندارد #CallStack:# android.database.sqlite.SQLiteConnection.nativePrepareStatement(Native Method)\nandroid.database.sqlite.SQLiteConnection.acquirePreparedStatement(SQLiteConnection.java:889)\nandroid.database.sqlite.SQLiteConnection.prepare(SQLiteConnection.java:500)\nandroid.database.sqlite.SQLiteSession.prepare(SQLiteSession.java:588)\nandroid.database.sqlite.SQLiteProgram.<init>(SQLiteProgram.java:58)\nandroid.database.sqlite.SQLiteQuery.<init>(SQLiteQuery.java:37)\nandroid.database.sqlite.SQLiteDirectCursorDriver.query(SQLiteDirectCursorDriver.java:44)\nandroid.database.sqlite.SQLiteDatabase.rawQueryWithFactory(SQLiteDatabase.java:1314)\nandroid.database.sqlite.SQLiteDatabase.rawQuery(SQLiteDatabase.java:1253)\nir.fardan7eghlim.tri.Models.SQLiteHandler.DatabaseHandler.getTasksByYearAndMonth(DatabaseHandler.java:1609)\nir.mirrajabi.persiancalendar.core.PersianCalendarHandler.getDays(PersianCalendarHandler.java:327)\nir.fardan7eghlim.tri.Views.Home.Fragments.CalndrFragment.initMonthTable(CalndrFragment.java:373)\nir.fardan7eghlim.tri.Views.Home.Fragments.CalndrFragment.onViewCreated(CalndrFragment.java:190)\nandroid.support.v4.app.FragmentManagerImpl.moveToState(FragmentManager.java:1430)\nandroid.support.v4.app.FragmentManagerImpl.moveFragmentToExpectedState(FragmentManager.java:1740)\nandroid.support.v4.app.FragmentManagerImpl.moveToState(FragmentManager.java:1809)\nandroid.support.v4.app.BackStackRecord.executeOps(BackStackRecord.java:799)\nandroid.support.v4.app.FragmentManagerImpl.executeOps(FragmentManager.java:2580)\nandroid.support.v4.app.FragmentManagerImpl.executeOpsTogether(FragmentManager.java:2367)\nandroid.support.v4.app.FragmentManagerImpl.removeRedundantOperationsAndExecute(FragmentManager.java:2322)\nandroid.support.v4.app.FragmentManagerImpl.execPendingActions(FragmentManager.java:2229)\nandroid.support.v4.app.FragmentManagerImpl$1.run(FragmentManager.java:700)\nandroid.os.Handler.handleCallback(Handler.java:733)\nandroid.os.Handler.dispatchMessage(Handler.java:95)\nandroid.os.Looper.loop(Looper.java:136)\nandroid.app.ActivityThread.main(ActivityThread.java:5017)\njava.lang.reflect.Method.invokeNative(Native Method)\njava.lang.reflect.Method.invoke(Method.java:515)\ncom.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:779)\ncom.android.internal.os.ZygoteInit.main(ZygoteInit.java:595)\ndalvik.system.NativeStart.main(Native Method)\n #Device Info:# OS version: 3.10.0+\nAPI level: 19\nManufacturer: Windroy\nDevice: windroye\nModel: Windroye\nProduct: windroye\nApp Version: 0.0.1\n', '2018-10-23 13:11:07', '2018-10-23 13:11:07', NULL),
(2, '5bcf210631dce5.17459904', NULL, ' خطای طرف گوشی ::dalvik.system.PathClassLoader', 'Main Message: message: خطای کلی رخ داد :Invalid int: \"null\"#LocalizedMessage:#Invalid int: \"null\"#Cause:#ندارد #CallStack:# java.lang.Integer.invalidInt(Integer.java:137)\njava.lang.Integer.parseInt(Integer.java:354)\njava.lang.Integer.parseInt(Integer.java:331)\njava.lang.Integer.valueOf(Integer.java:489)\nir.mirrajabi.persiancalendar.core.PersianCalendarHandler.getDays(PersianCalendarHandler.java:333)\nir.fardan7eghlim.tri.Views.Home.Fragments.CalndrFragment.initMonthTable(CalndrFragment.java:373)\nir.fardan7eghlim.tri.Views.Home.Fragments.CalndrFragment.onViewCreated(CalndrFragment.java:190)\nandroid.support.v4.app.FragmentManagerImpl.moveToState(FragmentManager.java:1430)\nandroid.support.v4.app.FragmentManagerImpl.moveFragmentToExpectedState(FragmentManager.java:1740)\nandroid.support.v4.app.FragmentManagerImpl.moveToState(FragmentManager.java:1809)\nandroid.support.v4.app.BackStackRecord.executeOps(BackStackRecord.java:799)\nandroid.support.v4.app.FragmentManagerImpl.executeOps(FragmentManager.java:2580)\nandroid.support.v4.app.FragmentManagerImpl.executeOpsTogether(FragmentManager.java:2367)\nandroid.support.v4.app.FragmentManagerImpl.removeRedundantOperationsAndExecute(FragmentManager.java:2322)\nandroid.support.v4.app.FragmentManagerImpl.execPendingActions(FragmentManager.java:2229)\nandroid.support.v4.app.FragmentManagerImpl$1.run(FragmentManager.java:700)\nandroid.os.Handler.handleCallback(Handler.java:733)\nandroid.os.Handler.dispatchMessage(Handler.java:95)\nandroid.os.Looper.loop(Looper.java:136)\nandroid.app.ActivityThread.main(ActivityThread.java:5017)\njava.lang.reflect.Method.invokeNative(Native Method)\njava.lang.reflect.Method.invoke(Method.java:515)\ncom.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:779)\ncom.android.internal.os.ZygoteInit.main(ZygoteInit.java:595)\ndalvik.system.NativeStart.main(Native Method)\n #Device Info:# OS version: 3.10.0+\nAPI level: 19\nManufacturer: Windroy\nDevice: windroye\nModel: Windroye\nProduct: windroye\nApp Version: 0.0.1\n', '2018-10-23 13:24:22', '2018-10-23 13:24:22', NULL),
(3, '5be00c3b93c4d2.22373646', NULL, ' خطای طرف گوشی ::dalvik.system.PathClassLoader', 'Main Message: message: خطای کلی رخ داد :ir.fardan7eghlim.tri.Views.Home.MainActivity@25b72db3 must implement OnFragmentInteractionListener#LocalizedMessage:#ir.fardan7eghlim.tri.Views.Home.MainActivity@25b72db3 must implement OnFragmentInteractionListener#Cause:#ندارد #CallStack:# ir.fardan7eghlim.tri.Views.Home.Fragments.ExchangeFragment.onAttach(ExchangeFragment.java:140)\nandroid.support.v4.app.FragmentManagerImpl.moveToState(FragmentManager.java:1363)\nandroid.support.v4.app.FragmentTransition.addToFirstInLastOut(FragmentTransition.java:1109)\nandroid.support.v4.app.FragmentTransition.calculateFragments(FragmentTransition.java:996)\nandroid.support.v4.app.FragmentTransition.startTransitions(FragmentTransition.java:99)\nandroid.support.v4.app.FragmentManagerImpl.executeOpsTogether(FragmentManager.java:2364)\nandroid.support.v4.app.FragmentManagerImpl.removeRedundantOperationsAndExecute(FragmentManager.java:2322)\nandroid.support.v4.app.FragmentManagerImpl.execPendingActions(FragmentManager.java:2229)\nandroid.support.v4.app.FragmentManagerImpl$1.run(FragmentManager.java:700)\nandroid.os.Handler.handleCallback(Handler.java:739)\nandroid.os.Handler.dispatchMessage(Handler.java:95)\nandroid.os.Looper.loop(Looper.java:135)\nandroid.app.ActivityThread.main(ActivityThread.java:5254)\njava.lang.reflect.Method.invoke(Native Method)\njava.lang.reflect.Method.invoke(Method.java:372)\ncom.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:903)\ncom.android.internal.os.ZygoteInit.main(ZygoteInit.java:698)\n #Device Info:# OS version: 3.10.0+\nAPI level: 22\nManufacturer: unknown\nDevice: generic_x86\nModel: Android SDK built for x86\nProduct: sdk_google_phone_x86\nApp Version: 0.0.1\n', '2018-11-05 09:24:11', '2018-11-05 09:24:11', NULL),
(4, '5be6bd1dddfe30.25965882', NULL, ' خطای طرف گوشی ::dalvik.system.PathClassLoader', 'Main Message: message: خطای کلی رخ داد :null#LocalizedMessage:#null#Cause:#ندارد #CallStack:# ir.mirrajabi.persiancalendar.core.PersianCalendarHandler.getDays(PersianCalendarHandler.java:399)\nir.fardan7eghlim.tri.Views.Home.Fragments.CalndrFragment.initMonthTable(CalndrFragment.java:373)\nir.fardan7eghlim.tri.Views.Home.Fragments.CalndrFragment.onViewCreated(CalndrFragment.java:190)\nandroid.support.v4.app.FragmentManagerImpl.moveToState(FragmentManager.java:1430)\nandroid.support.v4.app.FragmentManagerImpl.moveFragmentToExpectedState(FragmentManager.java:1740)\nandroid.support.v4.app.FragmentManagerImpl.moveToState(FragmentManager.java:1809)\nandroid.support.v4.app.BackStackRecord.executeOps(BackStackRecord.java:799)\nandroid.support.v4.app.FragmentManagerImpl.executeOps(FragmentManager.java:2580)\nandroid.support.v4.app.FragmentManagerImpl.executeOpsTogether(FragmentManager.java:2367)\nandroid.support.v4.app.FragmentManagerImpl.removeRedundantOperationsAndExecute(FragmentManager.java:2322)\nandroid.support.v4.app.FragmentManagerImpl.execPendingActions(FragmentManager.java:2229)\nandroid.support.v4.app.FragmentManagerImpl$1.run(FragmentManager.java:700)\nandroid.os.Handler.handleCallback(Handler.java:733)\nandroid.os.Handler.dispatchMessage(Handler.java:95)\nandroid.os.Looper.loop(Looper.java:136)\nandroid.app.ActivityThread.main(ActivityThread.java:5017)\njava.lang.reflect.Method.invokeNative(Native Method)\njava.lang.reflect.Method.invoke(Method.java:515)\ncom.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:779)\ncom.android.internal.os.ZygoteInit.main(ZygoteInit.java:595)\ndalvik.system.NativeStart.main(Native Method)\n #Device Info:# OS version: 3.10.0+\nAPI level: 19\nManufacturer: Windroy\nDevice: windroye\nModel: Windroye\nProduct: windroye\nApp Version: 0.0.1\n', '2018-11-10 11:12:29', '2018-11-10 11:12:29', NULL),
(5, '5bf111697c2215.10222002', NULL, ' خطای طرف گوشی ::dalvik.system.PathClassLoader', 'Main Message: message: خطای کلی رخ داد :Unable to start activity ComponentInfo{ir.fardan7eghlim.tri/ir.fardan7eghlim.tri.Views.Home.MainActivity}: java.lang.NullPointerException#LocalizedMessage:#Unable to start activity ComponentInfo{ir.fardan7eghlim.tri/ir.fardan7eghlim.tri.Views.Home.MainActivity}: java.lang.NullPointerException#Cause:#null #CallStack:# android.app.ActivityThread.performLaunchActivity(ActivityThread.java:2204)\nandroid.app.ActivityThread.handleLaunchActivity(ActivityThread.java:2254)\nandroid.app.ActivityThread.access$600(ActivityThread.java:141)\nandroid.app.ActivityThread$H.handleMessage(ActivityThread.java:1234)\nandroid.os.Handler.dispatchMessage(Handler.java:99)\nandroid.os.Looper.loop(Looper.java:137)\nandroid.app.ActivityThread.main(ActivityThread.java:5069)\njava.lang.reflect.Method.invokeNative(Native Method)\njava.lang.reflect.Method.invoke(Method.java:511)\ncom.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:793)\ncom.android.internal.os.ZygoteInit.main(ZygoteInit.java:560)\ndalvik.system.NativeStart.main(Native Method)\nCoused by:\nir.fardan7eghlim.tri.Views.Home.Fragments.HomeFragment.onViewCreated(HomeFragment.java:179)\nandroid.support.v4.app.FragmentManagerImpl.moveToState(FragmentManager.java:1430)\nandroid.support.v4.app.FragmentManagerImpl.moveFragmentToExpectedState(FragmentManager.java:1740)\nandroid.support.v4.app.FragmentManagerImpl.moveToState(FragmentManager.java:1809)\nandroid.support.v4.app.BackStackRecord.executeOps(BackStackRecord.java:799)\nandroid.support.v4.app.FragmentManagerImpl.executeOps(FragmentManager.java:2580)\nandroid.support.v4.app.FragmentManagerImpl.executeOpsTogether(FragmentManager.java:2367)\nandroid.support.v4.app.FragmentManagerImpl.removeRedundantOperationsAndExecute(FragmentManager.java:2322)\nandroid.support.v4.app.FragmentManagerImpl.execPendingActions(FragmentManager.java:2229)\nandroid.support.v4.app.FragmentManagerImpl.dispatchStateChange(FragmentManager.java:3221)\nandroid.support.v4.app.FragmentManagerImpl.dispatchActivityCreated(FragmentManager.java:3171)\nandroid.support.v4.app.FragmentController.dispatchActivityCreated(FragmentController.java:192)\nandroid.support.v4.app.FragmentActivity.onStart(FragmentActivity.java:560)\nandroid.support.v7.app.AppCompatActivity.onStart(AppCompatActivity.java:177)\nir.fardan7eghlim.tri.Models.Utility.BaseActivity.onStart(BaseActivity.java:88)\nir.fardan7eghlim.tri.Views.Home.MainActivity.onStart(MainActivity.java:279)\nandroid.app.Instrumentation.callActivityOnStart(Instrumentation.java:1176)\nandroid.app.Activity.performStart(Activity.java:5114)\nandroid.app.ActivityThread.performLaunchActivity(ActivityThread.java:2164)\nandroid.app.ActivityThread.handleLaunchActivity(ActivityThread.java:2254)\nandroid.app.ActivityThread.access$600(ActivityThread.java:141)\nandroid.app.ActivityThread$H.handleMessage(ActivityThread.java:1234)\nandroid.os.Handler.dispatchMessage(Handler.java:99)\nandroid.os.Looper.loop(Looper.java:137)\nandroid.app.ActivityThread.main(ActivityThread.java:5069)\njava.lang.reflect.Method.invokeNative(Native Method)\njava.lang.reflect.Method.invoke(Method.java:511)\ncom.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:793)\ncom.android.internal.os.ZygoteInit.main(ZygoteInit.java:560)\ndalvik.system.NativeStart.main(Native Method)\n #Device Info:# OS version: 3.4.0\nAPI level: 17\nManufacturer: Coolpad\nDevice: Coolpad8297W\nModel: Coolpad 8297W\nProduct: Coolpad8297W\nApp Version: 0.0.1\n', '2018-11-18 07:14:49', '2018-11-18 07:14:49', NULL),
(6, '5bf12a88509829.94788465', NULL, ' خطای طرف گوشی ::dalvik.system.PathClassLoader', 'Main Message: message: خطای کلی رخ داد :Unable to start activity ComponentInfo{ir.fardan7eghlim.tri/ir.fardan7eghlim.tri.Views.Home.MainActivity}: java.lang.NullPointerException#LocalizedMessage:#Unable to start activity ComponentInfo{ir.fardan7eghlim.tri/ir.fardan7eghlim.tri.Views.Home.MainActivity}: java.lang.NullPointerException#Cause:#null #CallStack:# android.app.ActivityThread.performLaunchActivity(ActivityThread.java:2204)\nandroid.app.ActivityThread.handleLaunchActivity(ActivityThread.java:2254)\nandroid.app.ActivityThread.access$600(ActivityThread.java:141)\nandroid.app.ActivityThread$H.handleMessage(ActivityThread.java:1234)\nandroid.os.Handler.dispatchMessage(Handler.java:99)\nandroid.os.Looper.loop(Looper.java:137)\nandroid.app.ActivityThread.main(ActivityThread.java:5069)\njava.lang.reflect.Method.invokeNative(Native Method)\njava.lang.reflect.Method.invoke(Method.java:511)\ncom.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:793)\ncom.android.internal.os.ZygoteInit.main(ZygoteInit.java:560)\ndalvik.system.NativeStart.main(Native Method)\nCoused by:\nir.fardan7eghlim.tri.Views.Home.Fragments.HomeFragment.onViewCreated(HomeFragment.java:179)\nandroid.support.v4.app.FragmentManagerImpl.moveToState(FragmentManager.java:1430)\nandroid.support.v4.app.FragmentManagerImpl.moveFragmentToExpectedState(FragmentManager.java:1740)\nandroid.support.v4.app.FragmentManagerImpl.moveToState(FragmentManager.java:1809)\nandroid.support.v4.app.BackStackRecord.executeOps(BackStackRecord.java:799)\nandroid.support.v4.app.FragmentManagerImpl.executeOps(FragmentManager.java:2580)\nandroid.support.v4.app.FragmentManagerImpl.executeOpsTogether(FragmentManager.java:2367)\nandroid.support.v4.app.FragmentManagerImpl.removeRedundantOperationsAndExecute(FragmentManager.java:2322)\nandroid.support.v4.app.FragmentManagerImpl.execPendingActions(FragmentManager.java:2229)\nandroid.support.v4.app.FragmentManagerImpl.dispatchStateChange(FragmentManager.java:3221)\nandroid.support.v4.app.FragmentManagerImpl.dispatchActivityCreated(FragmentManager.java:3171)\nandroid.support.v4.app.FragmentController.dispatchActivityCreated(FragmentController.java:192)\nandroid.support.v4.app.FragmentActivity.onStart(FragmentActivity.java:560)\nandroid.support.v7.app.AppCompatActivity.onStart(AppCompatActivity.java:177)\nir.fardan7eghlim.tri.Models.Utility.BaseActivity.onStart(BaseActivity.java:88)\nir.fardan7eghlim.tri.Views.Home.MainActivity.onStart(MainActivity.java:279)\nandroid.app.Instrumentation.callActivityOnStart(Instrumentation.java:1176)\nandroid.app.Activity.performStart(Activity.java:5114)\nandroid.app.ActivityThread.performLaunchActivity(ActivityThread.java:2164)\nandroid.app.ActivityThread.handleLaunchActivity(ActivityThread.java:2254)\nandroid.app.ActivityThread.access$600(ActivityThread.java:141)\nandroid.app.ActivityThread$H.handleMessage(ActivityThread.java:1234)\nandroid.os.Handler.dispatchMessage(Handler.java:99)\nandroid.os.Looper.loop(Looper.java:137)\nandroid.app.ActivityThread.main(ActivityThread.java:5069)\njava.lang.reflect.Method.invokeNative(Native Method)\njava.lang.reflect.Method.invoke(Method.java:511)\ncom.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:793)\ncom.android.internal.os.ZygoteInit.main(ZygoteInit.java:560)\ndalvik.system.NativeStart.main(Native Method)\n #Device Info:# OS version: 3.4.0\nAPI level: 17\nManufacturer: Coolpad\nDevice: Coolpad8297W\nModel: Coolpad 8297W\nProduct: Coolpad8297W\nApp Version: 0.0.1\n', '2018-11-18 09:02:00', '2018-11-18 09:02:00', NULL),
(7, '5bf135dfede357.05850488', NULL, ' خطای طرف گوشی ::dalvik.system.PathClassLoader', 'Main Message: message: خطای کلی رخ داد :Unable to start activity ComponentInfo{ir.fardan7eghlim.tri/ir.fardan7eghlim.tri.Views.Task.ShowTaskActivity}: java.lang.NullPointerException: Attempt to invoke virtual method \'java.lang.Boolean ir.fardan7eghlim.tri.Models.TaskModel.getHasAlarmSound()\' on a null object reference#LocalizedMessage:#Unable to start activity ComponentInfo{ir.fardan7eghlim.tri/ir.fardan7eghlim.tri.Views.Task.ShowTaskActivity}: java.lang.NullPointerException: Attempt to invoke virtual method \'java.lang.Boolean ir.fardan7eghlim.tri.Models.TaskModel.getHasAlarmSound()\' on a null object reference#Cause:#Attempt to invoke virtual method \'java.lang.Boolean ir.fardan7eghlim.tri.Models.TaskModel.getHasAlarmSound()\' on a null object reference #CallStack:# android.app.ActivityThread.performLaunchActivity(ActivityThread.java:2325)\nandroid.app.ActivityThread.handleLaunchActivity(ActivityThread.java:2387)\nandroid.app.ActivityThread.access$800(ActivityThread.java:151)\nandroid.app.ActivityThread$H.handleMessage(ActivityThread.java:1303)\nandroid.os.Handler.dispatchMessage(Handler.java:102)\nandroid.os.Looper.loop(Looper.java:135)\nandroid.app.ActivityThread.main(ActivityThread.java:5254)\njava.lang.reflect.Method.invoke(Native Method)\njava.lang.reflect.Method.invoke(Method.java:372)\ncom.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:903)\ncom.android.internal.os.ZygoteInit.main(ZygoteInit.java:698)\nCoused by:\nir.fardan7eghlim.tri.Models.SQLiteHandler.DatabaseHandler.getTaskById(DatabaseHandler.java:1742)\nir.fardan7eghlim.tri.Views.Task.ShowTaskActivity.onCreate(ShowTaskActivity.java:89)\nandroid.app.Activity.performCreate(Activity.java:5990)\nandroid.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1106)\nandroid.app.ActivityThread.performLaunchActivity(ActivityThread.java:2278)\nandroid.app.ActivityThread.handleLaunchActivity(ActivityThread.java:2387)\nandroid.app.ActivityThread.access$800(ActivityThread.java:151)\nandroid.app.ActivityThread$H.handleMessage(ActivityThread.java:1303)\nandroid.os.Handler.dispatchMessage(Handler.java:102)\nandroid.os.Looper.loop(Looper.java:135)\nandroid.app.ActivityThread.main(ActivityThread.java:5254)\njava.lang.reflect.Method.invoke(Native Method)\njava.lang.reflect.Method.invoke(Method.java:372)\ncom.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:903)\ncom.android.internal.os.ZygoteInit.main(ZygoteInit.java:698)\n #Device Info:# OS version: 3.10.0+\nAPI level: 22\nManufacturer: unknown\nDevice: generic_x86\nModel: Android SDK built for x86\nProduct: sdk_google_phone_x86\nApp Version: 0.0.1\n', '2018-11-18 09:50:23', '2018-11-18 09:50:23', NULL),
(8, '5bf14ef5b4ccc1.59296061', NULL, ' خطای طرف گوشی ::dalvik.system.PathClassLoader', 'Main Message: message: خطای کلی رخ داد :ir.fardan7eghlim.tri.Views.Home.MainActivity@53527364 must implement OnFragmentInteractionListener#LocalizedMessage:#ir.fardan7eghlim.tri.Views.Home.MainActivity@53527364 must implement OnFragmentInteractionListener#Cause:#ندارد #CallStack:# ir.fardan7eghlim.tri.Views.Home.Fragments.ExchangeFragment.onAttach(ExchangeFragment.java:140)\nandroid.support.v4.app.FragmentManagerImpl.moveToState(FragmentManager.java:1363)\nandroid.support.v4.app.FragmentManagerImpl.moveFragmentToExpectedState(FragmentManager.java:1740)\nandroid.support.v4.app.FragmentManagerImpl.moveToState(FragmentManager.java:1809)\nandroid.support.v4.app.BackStackRecord.executeOps(BackStackRecord.java:799)\nandroid.support.v4.app.FragmentManagerImpl.executeOps(FragmentManager.java:2580)\nandroid.support.v4.app.FragmentManagerImpl.executeOpsTogether(FragmentManager.java:2367)\nandroid.support.v4.app.FragmentManagerImpl.removeRedundantOperationsAndExecute(FragmentManager.java:2322)\nandroid.support.v4.app.FragmentManagerImpl.execPendingActions(FragmentManager.java:2229)\nandroid.support.v4.app.FragmentManagerImpl$1.run(FragmentManager.java:700)\nandroid.os.Handler.handleCallback(Handler.java:725)\nandroid.os.Handler.dispatchMessage(Handler.java:92)\nandroid.os.Looper.loop(Looper.java:137)\nandroid.app.ActivityThread.main(ActivityThread.java:5069)\njava.lang.reflect.Method.invokeNative(Native Method)\njava.lang.reflect.Method.invoke(Method.java:511)\ncom.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:793)\ncom.android.internal.os.ZygoteInit.main(ZygoteInit.java:560)\ndalvik.system.NativeStart.main(Native Method)\n #Device Info:# OS version: 3.4.0\nAPI level: 17\nManufacturer: Coolpad\nDevice: Coolpad8297W\nModel: Coolpad 8297W\nProduct: Coolpad8297W\nApp Version: 0.0.1\n', '2018-11-18 11:37:25', '2018-11-18 11:37:25', NULL),
(9, '5bf15954879039.39207848', NULL, ' خطای طرف گوشی ::dalvik.system.PathClassLoader', 'Main Message: message: خطای کلی رخ داد :Unable to create service ir.fardan7eghlim.tri.Views.Home.OverlayButton: android.view.WindowManager$BadTokenException: Unable to add window android.view.ViewRootImpl$W@f8c9d43 -- permission denied for window type 2002#LocalizedMessage:#Unable to create service ir.fardan7eghlim.tri.Views.Home.OverlayButton: android.view.WindowManager$BadTokenException: Unable to add window android.view.ViewRootImpl$W@f8c9d43 -- permission denied for window type 2002#Cause:#Unable to add window android.view.ViewRootImpl$W@f8c9d43 -- permission denied for window type 2002 #CallStack:# android.app.ActivityThread.handleCreateService(ActivityThread.java:3414)\nandroid.app.ActivityThread.-wrap4(Unknown Source:0)\nandroid.app.ActivityThread$H.handleMessage(ActivityThread.java:1683)\nandroid.os.Handler.dispatchMessage(Handler.java:105)\nandroid.os.Looper.loop(Looper.java:164)\nandroid.app.ActivityThread.main(ActivityThread.java:6541)\njava.lang.reflect.Method.invoke(Native Method)\ncom.android.internal.os.Zygote$MethodAndArgsCaller.run(Zygote.java:240)\ncom.android.internal.os.ZygoteInit.main(ZygoteInit.java:767)\nCoused by:\nandroid.view.ViewRootImpl.setView(ViewRootImpl.java:789)\nandroid.view.WindowManagerGlobal.addView(WindowManagerGlobal.java:356)\nandroid.view.WindowManagerImpl.addView(WindowManagerImpl.java:92)\nir.fardan7eghlim.tri.Views.Home.OverlayButton.onCreate(OverlayButton.java:137)\nandroid.app.ActivityThread.handleCreateService(ActivityThread.java:3404)\nandroid.app.ActivityThread.-wrap4(Unknown Source:0)\nandroid.app.ActivityThread$H.handleMessage(ActivityThread.java:1683)\nandroid.os.Handler.dispatchMessage(Handler.java:105)\nandroid.os.Looper.loop(Looper.java:164)\nandroid.app.ActivityThread.main(ActivityThread.java:6541)\njava.lang.reflect.Method.invoke(Native Method)\ncom.android.internal.os.Zygote$MethodAndArgsCaller.run(Zygote.java:240)\ncom.android.internal.os.ZygoteInit.main(ZygoteInit.java:767)\n #Device Info:# OS version: 3.18.91+\nAPI level: 26\nManufacturer: Google\nDevice: generic_x86\nModel: Android SDK built for x86\nProduct: sdk_gphone_x86\nApp Version: 0.0.1\n', '2018-11-18 12:21:40', '2018-11-18 12:21:40', NULL),
(10, '5cd1cb3d6c2b46.45544315', NULL, ' خطای طرف گوشی ::dalvik.system.PathClassLoader', 'Main Message: message: خطای کلی رخ داد :Unable to start service ir.fardan7eghlim.tri.Services.AdanService@26041a75 with Intent { cmp=ir.fardan7eghlim.tri/.Services.AdanService }: java.lang.IllegalStateException: The specified child already has a parent. You must call removeView() on the child\'s parent first.#LocalizedMessage:#Unable to start service ir.fardan7eghlim.tri.Services.AdanService@26041a75 with Intent { cmp=ir.fardan7eghlim.tri/.Services.AdanService }: java.lang.IllegalStateException: The specified child already has a parent. You must call removeView() on the child\'s parent first.#Cause:#The specified child already has a parent. You must call removeView() on the child\'s parent first. #CallStack:# android.app.ActivityThread.handleServiceArgs(ActivityThread.java:3152)\nandroid.app.ActivityThread.access$2100(ActivityThread.java:178)\nandroid.app.ActivityThread$H.handleMessage(ActivityThread.java:1568)\nandroid.os.Handler.dispatchMessage(Handler.java:111)\nandroid.os.Looper.loop(Looper.java:194)\nandroid.app.ActivityThread.main(ActivityThread.java:5637)\njava.lang.reflect.Method.invoke(Native Method)\njava.lang.reflect.Method.invoke(Method.java:372)\ncom.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:959)\ncom.android.internal.os.ZygoteInit.main(ZygoteInit.java:754)\nCoused by:\nandroid.view.ViewGroup.addViewInner(ViewGroup.java:4066)\nandroid.view.ViewGroup.addView(ViewGroup.java:3916)\nandroid.view.ViewGroup.addView(ViewGroup.java:3857)\nandroid.view.ViewGroup.addView(ViewGroup.java:3830)\nir.fardan7eghlim.tri.Services.AdanService.showCloseBtn(AdanService.java:360)\nir.fardan7eghlim.tri.Services.AdanService.playAzan(AdanService.java:102)\nir.fardan7eghlim.tri.Services.AdanService.onStartCommand(AdanService.java:508)\nandroid.app.ActivityThread.handleServiceArgs(ActivityThread.java:3135)\nandroid.app.ActivityThread.access$2100(ActivityThread.java:178)\nandroid.app.ActivityThread$H.handleMessage(ActivityThread.java:1568)\nandroid.os.Handler.dispatchMessage(Handler.java:111)\nandroid.os.Looper.loop(Looper.java:194)\nandroid.app.ActivityThread.main(ActivityThread.java:5637)\njava.lang.reflect.Method.invoke(Native Method)\njava.lang.reflect.Method.invoke(Method.java:372)\ncom.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:959)\ncom.android.internal.os.ZygoteInit.main(ZygoteInit.java:754)\n #Device Info:# OS version: 3.10.65\nAPI level: 22\nManufacturer: G6\nDevice: G6\nModel: G6\nProduct: G6\nApp Version: 0.0.1\n', '2019-05-07 18:15:25', '2019-05-07 18:15:25', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `payments`
--

CREATE TABLE `payments` (
  `payment_id` bigint(20) NOT NULL,
  `payment_guid` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `mobile_number` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL,
  `amount` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `service` tinyint(4) NOT NULL,
  `params` tinyint(4) NOT NULL DEFAULT '0',
  `payment_status` tinyint(4) DEFAULT NULL,
  `credit_status` tinyint(4) DEFAULT NULL,
  `description` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL,
  `authority` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `followup` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `transaction_id` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `unique_identifier` varchar(1000) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `payments`
--

INSERT INTO `payments` (`payment_id`, `payment_guid`, `mobile_number`, `amount`, `service`, `params`, `payment_status`, `credit_status`, `description`, `authority`, `followup`, `transaction_id`, `unique_identifier`, `created_at`, `updated_at`, `deleted_at`) VALUES
(1, '5af6f68d9f4131.53312488', '09122563621', '100', 1, 0, 100, NULL, 'خرید شارژ در اپ مثلث', '000000000000000000000000000074303106', '-1', '0', 'hhggdd', '2018-05-12 14:13:33', '2018-05-12 14:13:33', NULL),
(2, '5af6f68ddf4131.53312488', '09122563621', '100', 1, 0, 100, NULL, 'خرید شارژ در اپ مثلث', '000000000000000000000000000074303106', '-1', '0', 'hhggdd', '2018-05-12 14:13:33', '2018-05-12 14:13:33', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` bigint(11) NOT NULL,
  `user_guid` varchar(36) NOT NULL,
  `email` varchar(260) NOT NULL,
  `user_code` varchar(300) NOT NULL,
  `mobile` varchar(20) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `feedback`
--
ALTER TABLE `feedback`
  ADD PRIMARY KEY (`feedback_id`),
  ADD UNIQUE KEY `feedback_guid` (`feedback_guid`);

--
-- Indexes for table `log_event`
--
ALTER TABLE `log_event`
  ADD PRIMARY KEY (`log_event_id`),
  ADD UNIQUE KEY `log_event_guid` (`log_event_guid`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `payments`
--
ALTER TABLE `payments`
  ADD PRIMARY KEY (`payment_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `user_guid` (`user_guid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `feedback`
--
ALTER TABLE `feedback`
  MODIFY `feedback_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `log_event`
--
ALTER TABLE `log_event`
  MODIFY `log_event_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `payments`
--
ALTER TABLE `payments`
  MODIFY `payment_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` bigint(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `log_event`
--
ALTER TABLE `log_event`
  ADD CONSTRAINT `fk_users?log_event` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
