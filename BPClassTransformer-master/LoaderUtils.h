/*
 * Copyright 2021 AFYaan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License atv
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#pragma once
#include <windows.h>
#include <string>
#include "jni.h"

using namespace std;

string GetExeDir();

string GetCurrentExeName();

jobjectArray CharArrayToJavaStringArray(JNIEnv* env, const char* data[], int size);

jbyteArray CharArrayToJavaByteArray(JNIEnv* env, const unsigned char data[], int len);
