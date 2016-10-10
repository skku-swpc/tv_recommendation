
1. Create Model
	In Upload.upload() function, you can input your data. Data should be contained in file that stored the code.

	You make files that Program data.txt, Viewing data.txt.
	
	Each file format is like this.
	1) Program data.txt
	date, channel, program start time, program end time	, program id, title
	
	2) Viewing data.txt
	date,  family id, personal id, channel, genre, view start time, view end time, program start time, program end time, rating, program id, title

	We give you example txt file in data folder.
	program data example.txt
	vieweing data example.txt
	
2. Recommend TV Program

	If you want to get recommended tv programs, you should input group id, member id, date at Recommend.recommend(group id, member id, date).

	