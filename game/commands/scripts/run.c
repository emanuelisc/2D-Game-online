#include <stdlib.h>
#include <stdio.h>

int main() {
    printf("%s\n", "running...");
    system("cd .. && java -cp bin:res com.zelda.game.GameLauncher");
    printf("%s\n", "Done! - running");

    return 0;
}
