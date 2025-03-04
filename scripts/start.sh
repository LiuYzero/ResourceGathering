export SDKMAN_DIR="$HOME/.sdkman"
[[ -s "$HOME/.sdkman/bin/sdkman-init.sh" ]] && source "$HOME/.sdkman/bin/sdkman-init.sh"

nohup java -Xmx128m -Xms128m -jar ./$(ls *.jar) > /dev/null 2>&1 &
