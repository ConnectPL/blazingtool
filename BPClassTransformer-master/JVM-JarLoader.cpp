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

#define _CRT_SECURE_NO_WARNINGS

#define WIN32_LEAN_AND_MEAN
void listagraczyihuj();
#include "Loader.h"
#include "LoaderUtils.h"
#include "Resources.h"
#include "Utils.h"
#include "includes/MinHook.h"
#define GLEW_STATIC
#include "includes/glew.h"
//#pragma comment(lib, "libMinHook-x64-v141-mtd.lib")
#include <gl/gl.h> 
#pragma comment(lib,"opengl32.lib")
#pragma comment(lib, "d3d9.lib")
#include <Windows.h>
#include <winsock2.h>
#pragma comment(lib,"WS2_32.Lib")
#include <d3d9.h>
#include "minecraftFont.h"
#include "cheats/gui/imgui.h"
#include "cheats/gui/imgui_impl_win32.h"
//#include "cheats/gui/imgui_impl_dx9.h"
#include "cheats/gui/imgui_impl_opengl2.h"
#include "nigger.h"
#include "BpPlayer.h"
#include "cheats/icons/icons.h"
#include "cheats/icons/iconcpp.h"
#include "cheats/icons/font.h"
#include "Radius.h"
using namespace std;
JavaVM* jvm_;
JNIEnv* env_;
typedef BOOL(__stdcall* twglSwapBuffers) (_In_ HDC hDc);
typedef BOOL(__stdcall* tglCallList) (GLuint list);
typedef INT(WSAAPI* tWSASend) (
    SOCKET                             s,
    LPWSABUF                           lpBuffers,
    DWORD                              dwBufferCount,
    LPDWORD                            lpNumberOfBytesSent,
    DWORD                              dwFlags,
    LPWSAOVERLAPPED                    lpOverlapped,
    LPWSAOVERLAPPED_COMPLETION_ROUTINE lpCompletionRoutine);
twglSwapBuffers oSwapBuffers = NULL;
tglCallList oglCallList;
typedef void(__stdcall* T_niggerv2)(float, float, float);
T_niggerv2 pniggerv2 = nullptr;
typedef LRESULT(CALLBACK* WndProc_t) (HWND, UINT, WPARAM, LPARAM);
WndProc_t o_wndproc;


LPCSTR TargetProcess = "javaw.exe";

string ExeDir() {
    char result[MAX_PATH];
    string dir = string(result, GetModuleFileNameA(NULL, result, MAX_PATH));
    size_t last = dir.find_last_of("\\");

    return dir.substr(0, last);
}


HWND window;
bool done = false;
extern LRESULT ImGui_ImplWin32_WndProcHandler(HWND hWnd, UINT msg, WPARAM wParam, LPARAM lParam);
LRESULT CALLBACK WndProc(HWND hWnd, UINT uMsg, WPARAM wParam, LPARAM lParam)
{


    if (cheats::showmenu)
    {
        ImGui_ImplWin32_WndProcHandler(hWnd, uMsg, wParam, lParam);
        return true;
    }

    return CallWindowProc(o_wndproc, hWnd, uMsg, wParam, lParam);
}
static bool ContextCreated = false;
static HGLRC g_Context = (HGLRC)NULL;
tWSASend oWSASend;
bool blink = false;
int WSAAPI hkWSASend(
    SOCKET                             s,
    LPWSABUF                           lpBuffers,
    DWORD                              dwBufferCount,
    LPDWORD                            lpNumberOfBytesSent,
    DWORD                              dwFlags,
    LPWSAOVERLAPPED                    lpOverlapped,
    LPWSAOVERLAPPED_COMPLETION_ROUTINE lpCompletionRoutine
) {


    if (blink) {

        return 0;
    }
    else {
        return oWSASend(s, lpBuffers, dwBufferCount, lpNumberOfBytesSent, dwFlags, lpOverlapped, lpCompletionRoutine);
    }
}
static const ImWchar ranges[] =
{
    0x0020, 0x00FF,
    0x0400, 0x044F,
    0
};
vector<BpPlayer> items;
bool player_getter(void* data, int index, const char** output)
{
    BpPlayer player = items[index];


    *output = env_->GetStringUTFChars(player.userName, nullptr);
    return true;
}
void stylee()
{
    ImGui::GetIO().WantCaptureMouse = cheats::showmenu;
    ImGui::GetIO().MouseDrawCursor = cheats::showmenu;
    ImGuiStyle& style = ImGui::GetStyle();
    style.WindowMinSize = ImVec2(555, 348);
    style.Colors[ImGuiCol_TitleBg] = ImColor(23, 23, 23, 255);
    style.Colors[ImGuiCol_TitleBgActive] = ImColor(35, 35, 35, 255);
    style.Colors[ImGuiCol_TitleBgCollapsed] = ImColor(35, 35, 35, 255);
    style.Colors[ImGuiCol_FrameBg] = ImColor(54, 54, 54, 255);
    style.Colors[ImGuiCol_FrameBgActive] = ImColor(72, 76, 77, 255);
    style.Colors[ImGuiCol_FrameBgHovered] = ImColor(72, 76, 77, 255);
    style.Colors[ImGuiCol_CheckMark] = ImColor(255, 255, 255, 255);
    style.Colors[ImGuiCol_SliderGrab] = ImColor(181, 189, 188, 255);
    style.Colors[ImGuiCol_SliderGrabActive] = ImColor(181, 189, 188, 255);
    style.Colors[ImGuiCol_ButtonActive] = ImColor(23, 23, 23);
    style.Colors[ImGuiCol_ButtonHovered] = ImColor(23, 23, 23);
    style.Colors[ImGuiCol_Button] = ImColor(23, 23, 23);
    style.Colors[ImGuiCol_Text] = ImColor(255, 255, 255, 255);
    style.Colors[ImGuiCol_WindowBg] = ImColor(23, 23, 23, 255);
    style.Colors[ImGuiCol_ChildBg] = ImColor(23, 23, 23, 255);
    style.Colors[ImGuiCol_TabActive] = ImColor(23, 23, 23, 255);
    style.Colors[ImGuiCol_TabHovered] = ImColor(23, 23, 23, 255);
    style.Colors[ImGuiCol_TabUnfocused] = ImColor(28, 28, 39, 255);
    style.Colors[ImGuiCol_TabUnfocusedActive] = ImColor(23, 23, 23, 255);
    style.Colors[ImGuiCol_Tab] = ImColor(23, 23, 23, 255);
    style.WindowRounding = 6;
    style.FrameRounding = 6;
    style.GrabRounding = 6;
    style.TabRounding = 6;
    style.WindowBorderSize = false;
    style.ChildRounding = 6;
}
jmethodID userNameGet;
jint playersSize;
jstring userNamegraczza;
bool niemawtabie = false;
static ImVec4 active = ImColor(35, 120, 247);
static ImVec4 inactive = ImColor(65, 71, 67);
void render(_In_ HDC hDc)
{
    window = WindowFromDC(hDc);
    HGLRC oContext = wglGetCurrentContext();
    if(!done)
    {

        ImGui::CreateContext();
        ImGuiIO& io = ImGui::GetIO();
        ImFontConfig m_config;
        static const ImWchar icons_ranges[] = { 0xf000, 0xf3ff, 0 };
        ImFontConfig icons_config;
        ImFontConfig CustomFont;
        CustomFont.FontDataOwnedByAtlas = false;
        m_config.OversampleH = m_config.OversampleV = 3;
        m_config.PixelSnapH = false;
        icons_config.MergeMode = true;
        icons_config.PixelSnapH = true;
        icons_config.OversampleH = 2.5;
        icons_config.OversampleV = 2.5;
        io.ConfigFlags = ImGuiConfigFlags_NoMouseCursorChange;

        io.Fonts->AddFontFromMemoryTTF(const_cast<std::uint8_t*>(Custom), sizeof(Custom), 17.f, &CustomFont);
        io.Fonts->AddFontFromMemoryCompressedTTF(font_awesome_data, font_awesome_size, 17.0f, &icons_config, icons_ranges);


        io.Fonts->AddFontDefault();
        ImGui_ImplWin32_Init(window);
        ImGui_ImplOpenGL2_Init();
        done = true;
    }

    if (!o_wndproc)  o_wndproc = (WndProc_t)SetWindowLongPtr(window, GWLP_WNDPROC, (LONG_PTR)WndProc);

    ImGui_ImplOpenGL2_NewFrame();
    ImGui_ImplWin32_NewFrame();
    ImGui::NewFrame();
    stylee();
    if(cheats::showmenu)
    {
        ImGui::SetColorEditOptions(ImGuiColorEditFlags_NoInputs);
        ImGui::Begin("##xyz", 0, ImGuiWindowFlags_NoTitleBar | ImGuiColorEditFlags_NoInputs | ImGuiWindowFlags_NoResize);
        ImGui::BeginChild("##Bar", ImVec2(110, 333), false);
        ImGui::Spacing();
        ImGui::Spacing();
        ImGui::Spacing();
        ImGui::SameLine(5);
        ImGui::Text(ICON_FA_FIRE" blazingtool");
        ImGui::Spacing();
        ImGui::PushStyleColor(ImGuiCol_Text, active);
        ImGui::Text("          (vip)");
        ImGui::Text("__________");
        ImGui::PopStyleColor();
        ImGui::Spacing();
        ImGui::Spacing();
        ImGui::Spacing();
        ImGui::PushStyleColor(ImGuiCol_Separator, inactive);
        ImGui::Spacing();
        ImGui::Spacing();
        ImGui::Spacing();
        ImGui::PushStyleColor(ImGuiCol_Separator, tab == 1 ? active : inactive);
        ImGui::SameLine(5);
        const char* tabs[] = {
            ICON_FA_MALE" Combat",
            ICON_FA_BOLT" Movement",
            ICON_FA_EYE " Visuals",
            ICON_FA_MAP " Players",
        };
        ImGui::BeginGroup();
        for (int i = 0; i < ARRAYSIZE(tabs); i++)
        {
            if (ImGui::Button(tabs[i], ImVec2(100, 34)))
                tab = i;
            ImGui::PushStyleColor(ImGuiCol_Separator, tab == i ? active : inactive);
            ImGui::Separator();
        }
        ImGui::EndGroup();
        ImGui::SameLine();
        ImGui::BeginGroup();
        ImGui::EndChild();
        if (tab == 0)
        {
            ImGui::SameLine();
            ImGui::BeginChild("##combat", ImVec2(420, 333), false);
            ImGui::newcheckbox("killaura", &cheats::killaura); ImGui::SameLine();  ImGui::Bind("##killaura bind", &binds::killaurabind);
            ImGui::SliderFloat("reach aura", &cheats::reach, 0, 6, "%f");
            ImGui::EndChild();
        }
        if (tab == 1)
        {
            ImGui::SameLine();
            ImGui::BeginChild("##movement", ImVec2(420, 333), false);
            ImGui::newcheckbox("Fly", &cheats::fly); ImGui::SameLine(0, 5); ImGui::Bind("##fly bind", &binds::flybind, ImVec2(45, 25));
            ImGui::newcheckbox("blink", &blink); ImGui::SameLine(0, 5); ImGui::Bind("##blink bind", &binds::blinkbind, ImVec2(45, 25));
            ImGui::newcheckbox("Vclip (down)", &cheats::vclip); ImGui::SameLine(0, 5); ImGui::Bind("##vclip bind", &binds::vclipbind, ImVec2(45, 25));
            ImGui::SliderFloat("vclip y", &cheats::vclipsize, 0.0, 20.0, "%f");
            ImGui::SliderFloat("new x", &cheats::newX, 0.0, 20.0, "%f");
            ImGui::SliderFloat("new y", &cheats::newY, 0.0, 20.0, "%f");
            ImGui::SliderFloat("new z", &cheats::newZ, 0.0, 20.0, "%f");
            ImGui::EndChild();
        }
        if (tab == 2)
        {
            ImGui::SameLine();
            ImGui::BeginChild("##visssuals", ImVec2(420, 333), false);
            ImGui::newcheckbox("boxy 3d", &cheats::boxes);
            if (cheats::boxes) {
                ImGui::SameLine(0, 130);
                static float color10[3] = { 1.0 };
                ImGui::ColorEdit4("##boxy 3d color", color10);
                color10tak = color10[0];
                color20tak = color10[1];
                color30tak = color10[2];
            }
            ImGui::newcheckbox("xray", &cheats::xray); ImGui::SameLine();  ImGui::Bind("##xray bind", &binds::xraybind);
            ImGui::newcheckbox("cavefinder", &cheats::cavefinder); ImGui::SameLine();  ImGui::Bind("##xdfsd bind", &binds::cavefinderbind);
            ImGui::newcheckbox("Clear ESP", &cheats::clearesp); ImGui::SameLine();  ImGui::Bind("##xeee", &binds::clearespbind);
            ImGui::SameLine(0, 5);
            ImGui::Text("(!)");
            if (ImGui::IsItemHovered(ImGuiHoveredFlags_AllowWhenDisabled))
            {
                ImGui::SetTooltip("TURN ON VBOS AND FAST RENDER TO MAKE IT WORK");
            }
            ImGui::newcheckbox("Player List", &cheats::listapedalow);
            ImGui::EndChild();
        }
            if (tab == 3) {
                niemawtabie = true;
                ImGui::SameLine();

                ImGui::BeginChild("##listapizd", ImVec2(420, 333), false);
                ImGui::Text("Lista: ");
                listagraczyihuj();
                ImGui::EndChild();
            }
        ImGui::EndChild();
        ImGui::End(); 
    }

    if (cheats::listapedalow)
    {
        if (cheats::showmenu) {
            ImGui::Begin("Player List", 0, ImGuiWindowFlags_NoResize | ImGuiWindowFlags_NoBackground);
        }
        else {
            ImGui::Begin("Player List", 0, ImGuiWindowFlags_NoTitleBar | ImGuiWindowFlags_NoResize | ImGuiWindowFlags_NoBackground);
        }

        listagraczyihuj();
        ImGui::End();

    }
    ImGui::EndFrame();
    ImGui::Render();
    ImGui_ImplOpenGL2_RenderDrawData(ImGui::GetDrawData());

    wglMakeCurrent(hDc, oContext);



    if (cheats::killaura)
    {
        jclass test = env_->FindClass("pl/afyaan/module/impl/toggle");
        env_->CallStaticVoidMethod(test, env_->GetStaticMethodID(env_->FindClass("pl/afyaan/module/impl/toggle"), "killauraOn", "()V"));
        //jclass test = env_->FindClass("pl/afyaan/module/impl/movement/Vclip");
        //env_->CallStaticVoidMethod(test, env_->GetStaticMethodID(env_->FindClass("pl/afyaan/module/impl/movement/Vclip"), "onEnable", "()V"));
    }
    else
    {
        cheats::killaura = false;
        jclass test = env_->FindClass("pl/afyaan/module/impl/toggle");
        env_->CallStaticVoidMethod(test, env_->GetStaticMethodID(env_->FindClass("pl/afyaan/module/impl/toggle"), "killauraOff", "()V"));

    }
    if(cheats::fly) 
    {
        jclass test = env_->FindClass("pl/afyaan/module/impl/toggle");
        env_->CallStaticVoidMethod(test, env_->GetStaticMethodID(env_->FindClass("pl/afyaan/module/impl/toggle"), "flyOn", "()V"));
        //jclass test = env_->FindClass("pl/afyaan/module/impl/movement/Vclip");
        //env_->CallStaticVoidMethod(test, env_->GetStaticMethodID(env_->FindClass("pl/afyaan/module/impl/movement/Vclip"), "onEnable", "()V"));
    }
    else
    {
        cheats::fly = false;
        jclass test = env_->FindClass("pl/afyaan/module/impl/toggle");
        env_->CallStaticVoidMethod(test, env_->GetStaticMethodID(env_->FindClass("pl/afyaan/module/impl/toggle"), "flyOff", "()V"));
    }
    if(cheats::vclip)
    {
        //for (int i = 0; i < 5; i++) {
            jclass test = env_->FindClass("pl/afyaan/module/impl/movement/Vclip");
            env_->CallStaticVoidMethod(test, env_->GetStaticMethodID(env_->FindClass("pl/afyaan/module/impl/movement/Vclip"), "onEnable", "()V"));
        //}
        cheats::vclip = false;
    }
    if (cheats::players) {
        
    }
    jclass stepsize = env_->FindClass("pl/afyaan/module/impl/toggle");
    env_->CallStaticVoidMethod(stepsize, env_->GetStaticMethodID(env_->FindClass("pl/afyaan/module/impl/toggle"), "stepSize", "(F)V"), cheats::stepsize);
	jclass motionX = env_->FindClass("pl/afyaan/module/impl/toggle");
    env_->CallStaticVoidMethod(motionX, env_->GetStaticMethodID(env_->FindClass("pl/afyaan/module/impl/toggle"), "setMotionX", "(D)V"), (double) cheats::motionXf);
    jclass motionY = env_->FindClass("pl/afyaan/module/impl/toggle");
    env_->CallStaticVoidMethod(motionY, env_->GetStaticMethodID(env_->FindClass("pl/afyaan/module/impl/toggle"), "setMotionY", "(D)V"), (double) cheats::motionYf);
    jclass motionZ = env_->FindClass("pl/afyaan/module/impl/toggle");
    env_->CallStaticVoidMethod(motionZ, env_->GetStaticMethodID(env_->FindClass("pl/afyaan/module/impl/toggle"), "setMotionZ", "(D)V"), (double) cheats::motionZf);
    jclass vclip = env_->FindClass("pl/afyaan/module/impl/toggle");
    env_->CallStaticVoidMethod(motionZ, env_->GetStaticMethodID(env_->FindClass("pl/afyaan/module/impl/toggle"), "setVclip", "(D)V"), (double)cheats::vclipsize);
    jclass newX = env_->FindClass("pl/afyaan/module/impl/toggle");
    env_->CallStaticVoidMethod(motionZ, env_->GetStaticMethodID(env_->FindClass("pl/afyaan/module/impl/toggle"), "setNewX", "(D)V"), (double)cheats::newX);
    jclass newY = env_->FindClass("pl/afyaan/module/impl/toggle");
    env_->CallStaticVoidMethod(motionZ, env_->GetStaticMethodID(env_->FindClass("pl/afyaan/module/impl/toggle"), "setNewY", "(D)V"), (double)cheats::newY);
    jclass newZ = env_->FindClass("pl/afyaan/module/impl/toggle");
    env_->CallStaticVoidMethod(motionZ, env_->GetStaticMethodID(env_->FindClass("pl/afyaan/module/impl/toggle"), "setNewZ", "(D)V"), (double)cheats::newZ);
    jclass reach = env_->FindClass("pl/afyaan/module/impl/toggle");
    env_->CallStaticVoidMethod(motionZ, env_->GetStaticMethodID(env_->FindClass("pl/afyaan/module/impl/toggle"), "killauraReach", "(D)V"), (double)cheats::reach);
    //jclass setName = env_->FindClass("pl/afyaan/module/impl/toggle");
    //->CallStaticVoidMethod(motionZ, env_->GetStaticMethodID(env_->FindClass("pl/afyaan/module/impl/toggle"), "setAuraName", "(Ljava/lang/String;)V"), cheats::auraname);
    /*
     * motion
     */

    if (cheats::motionX)
    {
        jclass test = env_->FindClass("pl/afyaan/module/impl/toggle");
        env_->CallStaticVoidMethod(test, env_->GetStaticMethodID(env_->FindClass("pl/afyaan/module/impl/toggle"), "motionXOn", "()V"));
    }
    else
    {
        cheats::motionX = false;
        jclass test = env_->FindClass("pl/afyaan/module/impl/toggle");
        env_->CallStaticVoidMethod(test, env_->GetStaticMethodID(env_->FindClass("pl/afyaan/module/impl/toggle"), "motionXOff", "()V"));

    }
    if (cheats::motionY)
    {
        jclass test = env_->FindClass("pl/afyaan/module/impl/toggle");
        env_->CallStaticVoidMethod(test, env_->GetStaticMethodID(env_->FindClass("pl/afyaan/module/impl/toggle"), "motionYOn", "()V"));
    }
    else
    {
        cheats::motionY = false;
        jclass test = env_->FindClass("pl/afyaan/module/impl/toggle");
        env_->CallStaticVoidMethod(test, env_->GetStaticMethodID(env_->FindClass("pl/afyaan/module/impl/toggle"), "motionYOff", "()V"));

    }
    if (cheats::motionZ)
    {
        jclass test = env_->FindClass("pl/afyaan/module/impl/toggle");
        env_->CallStaticVoidMethod(test, env_->GetStaticMethodID(env_->FindClass("pl/afyaan/module/impl/toggle"), "motionZOn", "()V"));
    }
    else
    {
        cheats::motionZ = false;
        jclass test = env_->FindClass("pl/afyaan/module/impl/toggle");
        env_->CallStaticVoidMethod(test, env_->GetStaticMethodID(env_->FindClass("pl/afyaan/module/impl/toggle"), "motionXOff", "()V"));

    }
    //jclass speedmine = env_->FindClass("pl/afyaan/module/impl/toggle");
    //env_->CallStaticVoidMethod(stepsize, env_->GetStaticMethodID(env_->FindClass("pl/afyaan/module/impl/toggle"), "setSpeedmine", "(F)V"), cheats::speedmine);
}
BOOL __stdcall hkSwapBuffers(_In_ HDC hDc)
{

    if (GetAsyncKeyState(VK_INSERT) & 1)
    {
		cheats::showmenu = !cheats::showmenu;
    }
    if (GetAsyncKeyState(binds::xraybind) & 1) {
        cheats::xray = !cheats::xray;
    }
    if (GetAsyncKeyState(binds::cavefinderbind) & 1) {
        cheats::cavefinder = !cheats::cavefinder;
    }
    if (GetAsyncKeyState(binds::clearespbind) & 1) {
        cheats::clearesp = !cheats::clearesp;
    }
    if (GetAsyncKeyState(binds::chams) & 1) {
        cheats::chams = !cheats::chams;
    }
    if (GetAsyncKeyState(binds::flybind) & 1) {
        cheats::fly = !cheats::fly;
    }
    if (GetAsyncKeyState(binds::vclipbind) & 1) {
        cheats::vclip = !cheats::vclip;
    } 
    if (GetAsyncKeyState(binds::playersbind) & 1) {
        cheats::players = !cheats::players;
    }
    if (GetAsyncKeyState(binds::killaurabind) & 1) {
        cheats::killaura = !cheats::killaura;
    }
    if (GetAsyncKeyState(binds::blinkbind) & 1) {
        blink = !blink;
    }
    render(hDc);
    return oSwapBuffers(hDc);
}
GLfloat  curcolor[4], position[4];
BOOL __stdcall hglCallList(GLuint list) {
    if (cheats::xray) {
        glDepthRange(1, 0);
        oglCallList(list);
        glDepthRange(0, 1);
    }
    else {
        glDepthRange(0, 1);
    }
    if (cheats::cavefinder) {
        //glGetFloatv(GL_CURRENT_COLOR, curcolor);
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        //glColor4f(curcolor[0], curcolor[1], curcolor[2], 1.0);
    }
    else {
        //glGetFloatv(GL_CURRENT_COLOR, curcolor);
        glEnable(GL_DEPTH_TEST);
        glDisable(GL_BLEND);
        // glPopMatrix();
        // glColor4f(curcolor[0], curcolor[1], curcolor[2], 1.0);
    }
    if (cheats::clearesp) {

        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        oglCallList(list);
        glCullFace(GL_FRONT_AND_BACK);

        //
    }
    else {
        glCullFace(GL_BACK);
        glDisable(GL_CULL_FACE);

    }
    return oglCallList(list);
}
void __stdcall glScalef(float x, float y, float z)  //nametagi and esp
{


    pniggerv2(x, y, z);
    if (cheats::boxes) {
        if (x == 0.9375F and y == 0.9375F and z == 0.9375F)
        {
            Radius radius(0.8F, 2.0F, 0.8F);
            glPushAttrib(GL_CLIENT_ALL_ATTRIB_BITS);
            glPushMatrix();

            glDisable(GL_TEXTURE_2D);
            glDisable(GL_CULL_FACE);
            glDisable(GL_LIGHTING);
            glDisable(GL_DEPTH_TEST);


            glTranslatef(0.0F, -1.0F, 0.0F);

            glColor3f(color10tak, color20tak, color30tak);


            glBegin(GL_LINES);
            glVertex3f(radius.x / 2, -radius.y / 2, radius.z / 2);
            glVertex3f(radius.x / 2, radius.y / 2, radius.z / 2);

            glVertex3f(-radius.x / 2, -radius.y / 2, radius.z / 2);
            glVertex3f(radius.x / 2, -radius.y / 2, radius.z / 2);

            glVertex3f(-radius.x / 2, -radius.y / 2, radius.z / 2);
            glVertex3f(-radius.x / 2, radius.y / 2, radius.z / 2);

            glVertex3f(-radius.x / 2, radius.y / 2, radius.z / 2);
            glVertex3f(radius.x / 2, radius.y / 2, radius.z / 2);

            glVertex3f(radius.x / 2, radius.y / 2, radius.z / 2);
            glVertex3f(radius.x / 2, radius.y / 2, -radius.z / 2);

            glVertex3f(radius.x / 2, radius.y / 2, -radius.z / 2);
            glVertex3f(radius.x / 2, -radius.y / 2, -radius.z / 2);

            glVertex3f(radius.x / 2, radius.y / 2, -radius.z / 2);
            glVertex3f(-radius.x / 2, radius.y / 2, -radius.z / 2);

            glVertex3f(-radius.x / 2, radius.y / 2, -radius.z / 2);
            glVertex3f(-radius.x / 2, radius.y / 2, radius.z / 2);

            glVertex3f(-radius.x / 2, radius.y / 2, -radius.z / 2);
            glVertex3f(-radius.x / 2, -radius.y / 2, -radius.z / 2);

            glVertex3f(-radius.x / 2, -radius.y / 2, -radius.z / 2);
            glVertex3f(radius.x / 2, -radius.y / 2, -radius.z / 2);

            glVertex3f(-radius.x / 2, -radius.y / 2, -radius.z / 2);
            glVertex3f(-radius.x / 2, -radius.y / 2, radius.z / 2);

            glVertex3f(radius.x / 2, -radius.y / 2, -radius.z / 2);
            glVertex3f(radius.x / 2, -radius.y / 2, radius.z / 2);
            glEnd();



            glPopMatrix();

            glPopAttrib();
            glEnable(GL_DEPTH_TEST);
        }
    }
}
int main(int argsLength, const char* args[])
{
    /**
* opengl hook Xd
*/
    if (HMODULE hMod = GetModuleHandle("opengl32.dll")) {
        void* ptr = GetProcAddress(hMod, "wglSwapBuffers");
        void* call = GetProcAddress(hMod, "glCallList");
        void* ptrxd = GetProcAddress(hMod, "WSASend");
        void* nigger = GetProcAddress(hMod, "glScalef");
        MH_Initialize();
        MH_CreateHook(ptr, hkSwapBuffers, reinterpret_cast<void**>(&oSwapBuffers));
        MH_CreateHook(call, hglCallList, reinterpret_cast<void**>(&oglCallList));
        MH_CreateHook(ptrxd, hkWSASend, reinterpret_cast<void**>(&oWSASend));
        MH_CreateHook(nigger, glScalef, reinterpret_cast<void**>(&pniggerv2));
        MH_QueueEnableHook(ptr);
        MH_QueueEnableHook(call);
        MH_QueueEnableHook(ptrxd);
        MH_QueueEnableHook(nigger);
        MH_ApplyQueued();
    }
    Sleep(1000);
    string dir = ExeDir();
    string gameDir = dir + string("\\gameDir");
    string assetsDir = gameDir + string("\\assets");
    string jarPath = gameDir + string("\\versions\\blazingpack_1.8.8\\blazingpack_1.8.8.jar");
    string binariesDir = gameDir + string("\\bin\\binaries");
    string libraries = getLibraries(gameDir);

    string optClientJar = string("-Dminecraft.client.jar=") + jarPath;
    string optLibraryPath = string("-Djava.library.path=") + binariesDir;
    string optClassPath(string("-Djava.class.path=") + libraries);
    string mainMethod("pl/blazingpack/launcher/BlazingPackLauncher");
    unique_ptr<Loader> loader(new Loader(jarPath, mainMethod));

    loader->AddJVMArg("-Xmx2G");

    loader->AddJVMArg("-Xverify:none");
    loader->AddJVMArg("-XX:+UnlockExperimentalVMOptions");
    loader->AddJVMArg("-XX:+UseG1GC");
    loader->AddJVMArg("-XX:G1NewSizePercent=20");
    loader->AddJVMArg("-XX:G1ReservePercent=20");
    loader->AddJVMArg("-XX:MaxGCPauseMillis=50");
    loader->AddJVMArg("-XX:G1HeapRegionSize=32M");
    loader->AddJVMArg("-Xverify:none");
    loader->AddJVMArg(optLibraryPath.c_str());
    loader->AddJVMArg(optClientJar.c_str());
    loader->AddJVMArg(optClassPath.c_str());
    loader->AddArg("--username");
    loader->AddArg("niggaontheserw3r");
    loader->AddArg("--version");
    loader->AddArg("1.8.8");
    loader->AddArg("--gameDir");
    loader->AddArg(gameDir.c_str());
    loader->AddArg("--assetsDir");
    loader->AddArg(assetsDir.c_str());
    loader->AddArg("--assetIndex");
    loader->AddArg("1.8");  
    loader->AddArg("--uuid");
    loader->AddArg("");
    loader->AddArg("--accessToken");
    loader->AddArg("");
    loader->AddArg("--userProperties");
    loader->AddArg("{}");
    loader->AddArg("--userType");
    loader->AddArg("");

    loader->SetPassword("dupa");

    loader->RunFromMemory(rawData, sizeof(rawData), args, argsLength);
    jvm_ = loader->jvm;
    env_ = loader->env;
    loader->Run(args, argsLength);
    //return 0;
}

void listagraczyihuj() {
    jclass bpPlayer = env_->FindClass("pl/afyaan/module/impl/BpPlayer");
    jfieldID bpPlayers = env_->GetStaticFieldID(bpPlayer, "bpPlayers", "Ljava/util/List;");
    jobject players = env_->GetStaticObjectField(bpPlayer, bpPlayers);

    jclass listClass = env_->FindClass("java/util/ArrayList");
    jmethodID listSize = env_->GetMethodID(listClass, "size", "()I");
    jmethodID listGet = env_->GetMethodID(listClass, "get", "(I)Ljava/lang/Object;");
    playersSize = env_->CallIntMethod(players, listSize);

    items.clear();
    for (int i = 0; i < playersSize; i++) {
        jobject player = env_->CallObjectMethod(players, listGet, i);


        jfieldID userNameField = env_->GetFieldID(bpPlayer, "userName", "Ljava/lang/String;");
        userNameGet = env_->GetMethodID(bpPlayer, "getUserName", "()Ljava/lang/String;");
        jmethodID xGet = env_->GetMethodID(bpPlayer, "getX", "()D");
        jmethodID yGet = env_->GetMethodID(bpPlayer, "getY", "()D");
        jmethodID zGet = env_->GetMethodID(bpPlayer, "getZ", "()D");
        userNamegraczza = (jstring)env_->CallObjectMethod(player, userNameGet);
        int x = env_->CallDoubleMethod(player, xGet);
        int y = env_->CallDoubleMethod(player, yGet);
        int z = env_->CallDoubleMethod(player, zGet);
        string xfixed = to_string(x);
        string yfixed = to_string(y);
        string zfixed = to_string(z);
        string nickxddd = env_->GetStringUTFChars(userNamegraczza, 0);
        int dlugoscnicku = nickxddd.length();
        if (dlugoscnicku >= 3) {
            ImGui::Text(env_->GetStringUTFChars(userNamegraczza, 0));
            ImGui::SameLine();
            ImGui::Text("|");
            ImGui::SameLine();

            ImGui::Text("X:");

            ImGui::SameLine();

            ImGui::Text(xfixed.c_str());

            ImGui::SameLine();

            ImGui::Text("Y:");

            ImGui::SameLine();

            ImGui::Text(yfixed.c_str());

            ImGui::SameLine();

            ImGui::Text("Z:");

            ImGui::SameLine();

            ImGui::Text(zfixed.c_str());

        }
        //cout << env_->GetStringUTFChars(userNamegraczza, 0) << endl;

       // cout << userNameGet << "X: " << x << "Y: " << y << "Z" << z << endl;;

        BpPlayer newPlayer;
        newPlayer.userName = userNamegraczza;
        newPlayer.x = x;
        newPlayer.y = y;
        newPlayer.z = z;
        items.push_back(newPlayer);
    }

    // cout << playersSize << endl;
     //cheats::players = false;

}