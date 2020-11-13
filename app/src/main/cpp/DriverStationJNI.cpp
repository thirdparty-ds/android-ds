//
// Created by kaitlyn on 25/09/20.
//
#include <jni.h>
#include <libDS.h>
#include <android/log.h>

JNIEnv* gEnv;
jobject gCallbackSupplier;
jmethodID gCallbackId;

void consumerWrapper(StdoutMessage msg) {
    if(gEnv != nullptr) {
        jstring str = gEnv->NewStringUTF(msg.message);
        gEnv->CallObjectMethod(gCallbackSupplier, gCallbackId, str);
    }
}

extern "C"
JNIEXPORT jlong JNICALL
Java_com_redrield_androidds_ds_RealDriverStation_new(JNIEnv *env, jclass thiz, jint team,
                                                     jlong alliance) {
    return (jlong) DS_DriverStation_new_team(team, reinterpret_cast<Alliance*>(alliance));
}
extern "C"
JNIEXPORT void JNICALL
Java_com_redrield_androidds_ds_RealDriverStation_destroy(JNIEnv *env, jclass thiz, jlong ptr) {
    DS_DriverStation_destroy(reinterpret_cast<DriverStation*>(ptr));
}
extern "C"
JNIEXPORT jfloat JNICALL
Java_com_redrield_androidds_ds_RealDriverStation__1getBatteryVoltage(JNIEnv *env, jclass thiz,
                                                                     jlong ptr) {
    return DS_DriverStation_battery_voltage(reinterpret_cast<DriverStation*>(ptr));
}
extern "C"
JNIEXPORT void JNICALL
Java_com_redrield_androidds_ds_RealDriverStation__1enable(JNIEnv *env, jclass thiz, jlong ptr) {
    DS_DriverStation_enable(reinterpret_cast<DriverStation*>(ptr));
}
extern "C"
JNIEXPORT void JNICALL
Java_com_redrield_androidds_ds_RealDriverStation__1disable(JNIEnv *env, jclass thiz, jlong ptr) {
    DS_DriverStation_disable(reinterpret_cast<DriverStation*>(ptr));
}
extern "C"
JNIEXPORT jboolean JNICALL
Java_com_redrield_androidds_ds_RealDriverStation__1isEnabled(JNIEnv *env, jclass thiz, jlong ptr) {
    return DS_DriverStation_enabled(reinterpret_cast<DriverStation*>(ptr));
}
extern "C"
JNIEXPORT void JNICALL
Java_com_redrield_androidds_ds_RealDriverStation__1estop(JNIEnv *env, jclass thiz, jlong ptr) {
    DS_DriverStation_estop(reinterpret_cast<DriverStation*>(ptr));
}
extern "C"
JNIEXPORT jboolean JNICALL
Java_com_redrield_androidds_ds_RealDriverStation__1isEstopped(JNIEnv *env, jclass thiz, jlong ptr) {
    return DS_DriverStation_estopped(reinterpret_cast<DriverStation*>(ptr));
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_redrield_androidds_ds_RealDriverStation__1getDSMode(JNIEnv *env, jclass thiz, jlong ptr) {
    DsMode mode;
    auto* ds = reinterpret_cast<DriverStation*>(ptr);
    int ret = DS_DriverStation_get_ds_mode(ds, &mode);
    if(ret != 0) {
        jclass exceptionClass = env->FindClass("com/redrield/androidds/ds/LibDSError");
        env->ThrowNew(exceptionClass, "Nonzero return value from get_ds_mode");
    }

    return static_cast<jint>(mode);
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_redrield_androidds_ds_RealDriverStation__1getMode(JNIEnv *env, jclass thiz, jlong ptr) {
    Mode mode;
    auto* ds = reinterpret_cast<DriverStation*>(ptr);
    int ret = DS_DriverStation_get_mode(ds, &mode);
    if(ret != 0) {
        jclass exceptionClass = env->FindClass("com/redrield/androidds/ds/LibDSError");
        env->ThrowNew(exceptionClass, "Nonzero return value from get_mode");
    }

    return static_cast<jint>(mode);
}
extern "C"
JNIEXPORT void JNICALL
Java_com_redrield_androidds_ds_RealDriverStation__1setMode(JNIEnv *env, jclass thiz, jlong ptr,
                                                           jint mode) {
    Mode _mode = static_cast<Mode>(mode);
    DS_DriverStation_set_mode(reinterpret_cast<DriverStation*>(ptr), _mode);
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_redrield_androidds_ds_RealDriverStation__1getTeamNumber(JNIEnv *env, jclass thiz, jlong ptr) {
    return DS_DriverStation_get_team_number(reinterpret_cast<DriverStation*>(ptr));
}
extern "C"
JNIEXPORT void JNICALL
Java_com_redrield_androidds_ds_RealDriverStation__1restartCode(JNIEnv *env, jclass thiz, jlong ptr) {
    DS_DriverStation_restart_code(reinterpret_cast<DriverStation*>(ptr));
}
extern "C"
JNIEXPORT void JNICALL
Java_com_redrield_androidds_ds_RealDriverStation__1restartRIO(JNIEnv *env, jclass thiz, jlong ptr) {
    DS_DriverStation_restart_roborio(reinterpret_cast<DriverStation*>(ptr));
}
extern "C"
JNIEXPORT void JNICALL
Java_com_redrield_androidds_ds_RealDriverStation__1setAlliance(JNIEnv *env, jclass thiz, jlong ptr,
                                                               jlong alliance) {
    DS_DriverStation_set_alliance(reinterpret_cast<DriverStation*>(ptr), reinterpret_cast<Alliance*>(alliance));
}
extern "C"
JNIEXPORT void JNICALL
Java_com_redrield_androidds_ds_RealDriverStation__1setGSM(JNIEnv *env, jclass thiz, jlong ptr,
                                                          jstring gsm) {
    const char* s = env->GetStringUTFChars(gsm, nullptr);
    DS_DriverStation_set_game_specific_message(reinterpret_cast<DriverStation*>(ptr), s);
    env->ReleaseStringUTFChars(gsm, s);
}
extern "C"
JNIEXPORT void JNICALL
Java_com_redrield_androidds_ds_RealDriverStation__1setTeamNumber(JNIEnv *env, jclass thiz, jlong ptr,
                                                                 jint team) {
    __android_log_print(ANDROID_LOG_INFO, "dsJNI", "Setting team number to %d", team);
    DS_DriverStation_set_team_number(reinterpret_cast<DriverStation*>(ptr),(uint32_t) team);
}
extern "C"
JNIEXPORT jboolean JNICALL
Java_com_redrield_androidds_ds_RealDriverStation__1isConnected(JNIEnv *env, jclass thiz, jlong ptr) {
    return (DS_DriverStation_trace(reinterpret_cast<DriverStation*>(ptr)) & (uint8_t) TRACE_IS_ROBORIO) != 0;
}
extern "C"
JNIEXPORT jboolean JNICALL
Java_com_redrield_androidds_ds_RealDriverStation__1isCodeAlive(JNIEnv *env, jclass thiz, jlong ptr) {
    return (DS_DriverStation_trace(reinterpret_cast<DriverStation*>(ptr)) & (uint8_t) TRACE_ROBOT_CODE) != 0;
}
extern "C"
JNIEXPORT void JNICALL
Java_com_redrield_androidds_ds_RealDriverStation__1setTCPConsumer(JNIEnv *env, jclass thiz, jlong ptr,
                                                                  jobject callback) {
    gEnv = env;
    gCallbackSupplier = callback;
    jclass clazz = env->GetObjectClass(callback);
    gCallbackId = env->GetMethodID(clazz, "invoke", "(Ljava/lang/String;)V");
    DS_DriverStation_set_tcp_consumer(reinterpret_cast<DriverStation*>(ptr), consumerWrapper);
}