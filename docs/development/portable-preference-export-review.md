# Portable Preference Export Review

Status: Development source candidate.

GoreeCloud Keyboard's category-only portable export now uses a two-phase user-controlled flow. Before Android's Storage Access Framework is asked to create a destination document, Keyboard reads exactly the explicitly selected emoji category into a typed `ExportPreview` and shows that category plus the one-field privacy scope to the user.

Only after the user chooses **Choose file** does Keyboard launch `ACTION_CREATE_DOCUMENT`. The bytes written after the picker returns are encoded from the reviewed `ExportPreview`, not from mutable SharedPreferences state. A category change while the destination picker is open therefore cannot silently change the reviewed export.

The preview contains exactly one `EmojiCategory`. It cannot read or represent typed text, editor context, suggestions, learned input, emoji recents/frequency, search queries, clipboard data, key history, sensitive content, application/editor identifiers, telemetry identifiers, Identity data, credentials, or secrets.

Canceling the review does not ask Android to create a document. Selecting a destination still grants only the user-selected SAF URI; Keyboard requests no broad storage permission or network permission.

This does not create sync, backup, restore, Everkeep lineage, account/device ownership proof, production migration, or multi-setting portability. The file remains the existing strict `goreecloud-keyboard-preferences/1` one-field format and the product remains Development/nonconformant until its separate platform, accessibility, device, recovery, release, and Stable gates are satisfied.
