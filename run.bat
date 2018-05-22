@echo off
FOR %%s IN (.\islands\*) DO (
	echo Running %%~ns
	java Blobworld < %%s > .\islands\output\%%~ns.txt
	java TestBlobs %%s .\islands\output\%%~ns.txt
)
pause