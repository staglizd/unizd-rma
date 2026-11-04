#!/usr/bin/env bash
# Preimenuje trenutni paket aplikacije u org.unizd.rma.<prezime>.
# Trenutni paket se čita iz `namespace` u app/build.gradle.kts (u referentnom repou je org.unizd.rma.demo).
# Upotreba (iz korijena projekta):  tools/rename-package.sh prezime   (malim slovima, bez dijakritika)
set -euo pipefail

NEW_NAME="${1:?usage: tools/rename-package.sh prezime}"
if ! [[ "$NEW_NAME" =~ ^[a-z][a-z0-9]*$ ]]; then
  echo "Prezime mora biti malim slovima, bez dijakritika i razmaka (npr. staglicic)." >&2
  exit 1
fi

cd "$(dirname "$0")/.."
GRADLE_FILE="app/build.gradle.kts"
OLD="$(sed -n 's/^[[:space:]]*namespace[[:space:]]*=[[:space:]]*"\([^"]*\)".*/\1/p' "$GRADLE_FILE" | head -n1)"
NEW="org.unizd.rma.${NEW_NAME}"

if [ -z "$OLD" ]; then echo "Ne mogu pročitati namespace iz $GRADLE_FILE" >&2; exit 1; fi
if [ "$OLD" = "$NEW" ]; then echo "Paket je već $NEW – ništa za napraviti."; exit 0; fi

OLD_PATH="$(printf '%s' "$OLD" | tr . /)"
NEW_PATH="$(printf '%s' "$NEW" | tr . /)"

# 1) premjesti direktorije izvornog koda (main, test, androidTest; java i kotlin)
for root in app/src/*/java app/src/*/kotlin; do
  [ -d "$root/$OLD_PATH" ] || continue
  mkdir -p "$root/$(dirname "$NEW_PATH")"
  if git ls-files --error-unmatch "$root/$OLD_PATH" >/dev/null 2>&1; then
    git mv "$root/$OLD_PATH" "$root/$NEW_PATH"
  else
    mv "$root/$OLD_PATH" "$root/$NEW_PATH"
  fi
done

# 2) zamijeni tekst paketa u datotekama (perl radi isto na macOS-u, Linuxu i Git Bashu)
grep -rlI --exclude-dir=build --exclude-dir=.gradle --exclude-dir=.git -F "$OLD" app \
  | xargs perl -pi -e "s/\Q$OLD\E/$NEW/g"

echo "Paket promijenjen: $OLD -> $NEW. U Android Studiju napravi Build > Clean Project."
