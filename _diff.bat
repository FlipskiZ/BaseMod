@echo off
diff -ru "../_lib/decompiled/com/megacrit/cardcrawl" "src/main/java/com/megacrit/cardcrawl" | sed '/^Only in/d' | sed 's@../_lib/decompiled@src/main/java@g' > "src/main/java/com/megacrit/cardcrawl/com.megacrit.cardcrawl.diff"
