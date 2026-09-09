# Keyboard Inspiration Input-Surface Development Record

Date: 2026-09-08
Status: Development

## Purpose

Record the user-approved GoreeCloud Keyboard input-surface direction derived from the supplied Android-keyboard inspiration references, distinguish inspiration from copied product design, and track which parts are implemented versus still planned.

The supplied references are design inspiration only. GoreeCloud Keyboard remains an original first-party implementation governed by GoreeCloud design, privacy, security, accessibility, continuity, and release requirements.

## Approved direction

The target experience combines:

- a clean, spacious QWERTY surface with rounded Glaze UI key geometry;
- an optional dedicated 1–0 number row;
- a local suggestion strip above the key field;
- an adaptive editor action key for Enter, Go, Search, Send, Next, Done, and Previous semantics;
- direct symbols and emoji access with long-press alternates;
- Light/Dark system-adaptive presentation as the current runtime baseline;
- a future utility toolbar for explicitly implemented and governed tools such as Secure Paste/clipboard actions, emoji, GIF/sticker discovery, voice input, translation, settings, and approved GoreeCloud actions;
- future compact, one-handed, floating, split, tablet, foldable, and posture-aware layouts;
- future gesture/swipe typing and cursor/text-selection gestures;
- future multilingual input, language switching, stronger local prediction/correction, dictionaries, and user-controlled personalization;
- privacy-first processing, with local operation by default and explicit approval/controls before any remote or sensitive-data capability; and
- deeper GoreeCloud integration only through approved platform contracts rather than hidden coupling.

## Implemented in this Development tranche

This tranche adds real behavior rather than placeholder controls:

1. **Dedicated number row**
   - The native letters surface can render a separate 1–0 row above QWERTY.
   - It is enabled by default for the new inspiration-driven layout.
   - A device-local **Show number row** setting can disable or re-enable it.
   - The preference contains only the Boolean presentation choice and is not added to the portable preference format.

2. **Adaptive editor action key**
   - The native action key now reflects Android `EditorInfo` action semantics.
   - Supported presentations are Go, Search, Send, Next, Done, Previous, and ordinary Enter.
   - The accessibility label follows the semantic action rather than relying on the visual glyph alone.
   - The service uses `InputConnection.performEditorAction()` for explicit host actions and falls back to the ordinary Enter key path when no action is applicable or the host does not handle it.

3. **Input-surface sizing**
   - The preferred native keyboard height expands when the dedicated number row is enabled so the additional row does not silently shrink the designed key field into a denser layout.
   - Row spacing is derived from the actual row count.

4. **Settings surface**
   - Android IME settings now expose the number-row choice while preserving the existing bounded emoji-category import/export flow.
   - No network, clipboard, typed-text, learned-input, account, or telemetry authority is added.

## Existing behavior aligned with the inspiration direction

The current Development stack already provides first-party rounded Glaze key surfaces, Light/Dark Android night-mode adaptation, a local Quill suggestion strip, symbols pages, bounded emoji categories/recents/offline search, local long-press alternates, haptic feedback, and native virtual accessibility controls.

## Still planned — not implementation claims

The following approved inspiration goals remain active product obligations and are not represented as implemented by this tranche:

- configurable utility toolbar;
- Secure Paste/clipboard history and pinned snippets;
- GIF and sticker discovery;
- voice input;
- translation;
- gesture/swipe typing;
- cursor and selection gestures;
- multilingual layouts and language switching;
- manual Light/Dark overrides and broader appearance controls beyond current system adaptation;
- one-handed, floating, split, tablet, foldable, and posture-aware layouts;
- richer local correction/prediction and dictionaries;
- approved Quill writing assistance beyond the current local suggestion boundary; and
- Launcher/Search/other GoreeCloud ecosystem actions through explicit governed integration contracts.

Placeholder toolbar icons or labels do not satisfy these obligations. Each capability must have functional behavior, privacy/security authority, accessibility semantics, tests, lifecycle integration, and appropriate runtime acceptance before it can move to the implemented list.

## Acceptance boundary

This Development change does not establish Human Visual Excellence, representative physical-device ergonomics, TalkBack/Switch Access/Voice Access acceptance, Touch Assistance, large-text/reflow, RTL/localization, tablet/foldable acceptance, latency/performance/power acceptance, Privacy Shield/Wardveil/Everkeep acceptance, Release Candidate qualification, signing/distribution acceptance, or Stable status.
