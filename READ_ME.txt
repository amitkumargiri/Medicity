Task:
1. Add data insertion validation for add visit data.
2. Change the home screen design better.
3. Implement the back button on add visit screen.
4. Covert the activity with ListView to Scrollable activity.
5. Add error dialog box in case of any error occurs.
6. Make the aadhar no column with constarint unique and not null.
7. Implement confirm dialog box for deleting the item.

Coronavirus cases -
https://api.covid19india.org/data.json

Commands:

git status | grep app | cut -f2 -d":" | sed 's/ //g' |  perl -ne 'print "./$_"' | xargs -I '{}' cp '{}' ./temp/



Version 4.0
===========
What's new in this release?
- Add medical visit and track your expenses
- Implemented new user interface
- Track your child vaccination
- Track your health policies