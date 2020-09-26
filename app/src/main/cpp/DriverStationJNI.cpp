//
// Created by kaitlyn on 25/09/20.
//
#include <jni.h>
#include <libDS.h>

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
Java_com_redrield_androidds_ds_DriverStation_new(JNIEnv *env, jobject thiz, jint team,
                                                 jlong alliance) {
    return (jlong) DS_DriverStation_new_team(team, reinterpret_cast<Alliance*>(alliance));
}
extern "C"
JNIEXPORT void JNICALL
Java_com_redrield_androidds_ds_DriverStation_destroy(JNIEnv *env, jobject thiz, jlong ptr) {
    DS_DriverStation_destroy(reinterpret_cast<DriverStation*>(ptr));
}
extern "C"
JNIEXPORT jfloat JNICALL
Java_com_redrield_androidds_ds_DriverStation__1getBatteryVoltage(JNIEnv *env, jobject thiz,
                                                                 jlong ptr) {
    return DS_DriverStation_battery_voltage(reinterpret_cast<DriverStation*>(ptr));
}
extern "C"
JNIEXPORT void JNICALL
Java_com_redrield_androidds_ds_DriverStation__1enable(JNIEnv *env, jobject thiz, jlong ptr) {
    DS_DriverStation_enable(reinterpret_cast<DriverStation*>(ptr));
}
extern "C"
JNIEXPORT void JNICALL
Java_com_redrield_androidds_ds_DriverStation__1disable(JNIEnv *env, jobject thiz, jlong ptr) {
    DS_DriverStation_disable(reinterpret_cast<DriverStation*>(ptr));
}
extern "C"
JNIEXPORT jboolean JNICALL
Java_com_redrield_androidds_ds_DriverStation__1isEnabled(JNIEnv *env, jobject thiz, jlong ptr) {
    return DS_DriverStation_enabled(reinterpret_cast<DriverStation*>(ptr));
}
extern "C"
JNIEXPORT void JNICALL
Java_com_redrield_androidds_ds_DriverStation__1estop(JNIEnv *env, jobject thiz, jlong ptr) {
    DS_DriverStation_estop(reinterpret_cast<DriverStation*>(ptr));
}
extern "C"
JNIEXPORT jboolean JNICALL
Java_com_redrield_androidds_ds_DriverStation__1isEstopped(JNIEnv *env, jobject thiz, jlong ptr) {
    return DS_DriverStation_estopped(reinterpret_cast<DriverStation*>(ptr));
}
extern "C"
JNIEXPORT void JNICALL
Java_com_redrield_androidds_ds_DriverStation__1getDSMode(JNIEnv *env, jobject thiz, jlong ptr) {
    // TODO: implement _getDSMode()
}
extern "C"
JNIEXPORT void JNICALL
Java_com_redrield_androidds_ds_DriverStation__1getMode(JNIEnv *env, jobject thiz, jlong ptr) {
    // TODO: implement _getMode()
}
extern "C"
JNIEXPORT void JNICALL
Java_com_redrield_androidds_ds_DriverStation__1setMode(JNIEnv *env, jobject thiz, jlong ptr,
                                                       jobject mode) {
    // TODO: implement _setMode()
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_redrield_androidds_ds_DriverStation__1getTeamNumber(JNIEnv *env, jobject thiz, jlong ptr) {
    return DS_DriverStation_get_team_number(reinterpret_cast<DriverStation*>(ptr));
}
extern "C"
JNIEXPORT void JNICALL
Java_com_redrield_androidds_ds_DriverStation__1restartCode(JNIEnv *env, jobject thiz, jlong ptr) {
    DS_DriverStation_restart_code(reinterpret_cast<DriverStation*>(ptr));
}
extern "C"
JNIEXPORT void JNICALL
Java_com_redrield_androidds_ds_DriverStation__1restartRIO(JNIEnv *env, jobject thiz, jlong ptr) {
    DS_DriverStation_restart_roborio(reinterpret_cast<DriverStation*>(ptr));
}
extern "C"
JNIEXPORT void JNICALL
Java_com_redrield_androidds_ds_DriverStation__1setAlliance(JNIEnv *env, jobject thiz, jlong ptr,
                                                           jlong alliance) {
    DS_DriverStation_set_alliance(reinterpret_cast<DriverStation*>(ptr), reinterpret_cast<Alliance*>(alliance));
}
extern "C"
JNIEXPORT void JNICALL
Java_com_redrield_androidds_ds_DriverStation__1setGSM(JNIEnv *env, jobject thiz, jlong ptr,
                                                      jstring gsm) {
    const char* s = env->GetStringUTFChars(gsm, nullptr);
    DS_DriverStation_set_game_specific_message(reinterpret_cast<DriverStation*>(ptr), s);
    env->ReleaseStringUTFChars(gsm, s);
}
extern "C"
JNIEXPORT void JNICALL
Java_com_redrield_androidds_ds_DriverStation__1setTeamNumber(JNIEnv *env, jobject thiz, jlong ptr,
                                                             jint team) {
    DS_DriverStation_set_team_number(reinterpret_cast<DriverStation*>(ptr), team);
}
extern "C"
JNIEXPORT jboolean JNICALL
Java_com_redrield_androidds_ds_DriverStation__1isConnected(JNIEnv *env, jobject thiz, jlong ptr) {
    return (DS_DriverStation_trace(reinterpret_cast<DriverStation*>(ptr)) & (uint8_t) TRACE_IS_ROBORIO) != 0;
}
extern "C"
JNIEXPORT jboolean JNICALL
Java_com_redrield_androidds_ds_DriverStation__1isCodeAlive(JNIEnv *env, jobject thiz, jlong ptr) {
    return (DS_DriverStation_trace(reinterpret_cast<DriverStation*>(ptr)) & (uint8_t) TRACE_ROBOT_CODE) != 0;
}
extern "C"
JNIEXPORT void JNICALL
Java_com_redrield_androidds_ds_DriverStation__1setTCPConsumer(JNIEnv *env, jobject thiz, jlong ptr,
                                                              jobject callback) {
    gEnv = env;
    gCallbackSupplier = callback;
    jclass clazz = env->GetObjectClass(callback);
    gCallbackId = env->GetMethodID(clazz, "invoke", "(Ljava/lang/String;)V");
    DS_DriverStation_set_tcp_consumer(reinterpret_cast<DriverStation*>(ptr), consumerWrapper);
}