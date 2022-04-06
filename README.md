# Dictionary-Attack
## Project description
>This project is a very simple implementation of a dictionary attack.  
The password is supposed to be crypted with SHA-256.  
The attack is supported by a full english dictionary with also some italians words.
## How to install and run
It's very simple to try it! first thing to do is to download the repo and then compile the java files.
Once you've done that run PasswordManager.java to set the password. The password is gonna be crypted with SHA-256 and stored in the pwfile.txt.  
Note: The PATH are hard coded, just change them to make it work properly!  
Now just run DictionaryAttack.java and wait for the magic.
## Guess capability
At this moment the project supports only 4 guess patterns out of 8 :
- word | Word
- word.n | Word.n
- word_n | Word_n
- word<n> | Word<n>
  >> where n is a number between 0 and 1000
