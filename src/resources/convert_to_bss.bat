@echo off

for %%f in (*.css) do (
	"%JAVA_HOME%\bin\javapackager" -createbss -srcfiles "%cd%\%%f" -outdir .
	echo Compiled %%f to %%~nf.bss
)

echo.
echo Press any key to exit . . .
pause>nul
