# git status lib/ | xargs rm -rf
git ls-files lib/ --other --directory --exclude-standard | xargs rm 
