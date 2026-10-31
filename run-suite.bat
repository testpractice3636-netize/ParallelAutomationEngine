@echo off
echo ========================================================
echo   LAUNCHING MULTI-THREADED AUTOMATION SUITE VIA MAVEN   
echo ========================================================

:: 🚀 STEP 1: Bind your working Maven installation path
set M2_HOME=C:\QA_Automation\apache-maven-3.10.0

:: 🔎 STEP 2: AUTOMATED ECLIPSE JAVA SCANNER
:: This loop searches your hidden Eclipse folder pool for the embedded Java compiler!
echo [SYSTEM-TRACE] Scanning Eclipse metadata pools for hidden Java runtimes...

for /d %%i in ("C:\Users\HP\.p2\pool\plugins\org.eclipse.justj.openjdk.hotspot.jre.full.*") do (
    if exist "%%i\jsrc.zip" (
        set JAVA_HOME=%%i
    ) else if exist "%%i\bin\java.exe" (
        set JAVA_HOME=%%i
    )
)

:: 💡 STEP 3: EMERGENCY CLEANUP FALLBACK
:: If your Eclipse is installed in a different folder, this searches standard user app data paths
if "%JAVA_HOME%"=="" (
    for /d %%i in ("C:\Users\HP\eclipse\plugins\org.eclipse.justj.openjdk.hotspot.jre.full.*") do set JAVA_HOME=%%i
)

set PATH=%JAVA_HOME%\bin;%M2_HOME%\bin;C:\Windows\System32;%PATH%

echo --------------------------------------------------------
echo [SYSTEM-TRACE] Auto-Detected JAVA_HOME: %JAVA_HOME%
echo [SYSTEM-TRACE] Auto-Detected M2_HOME:   %M2_HOME%
echo --------------------------------------------------------

cd C:\QA_Automation\Workspace\ParallelAutomationEngine
call mvn clean test
pause
