# GoreeCloud Keyboard Specifications

## Product status

**Development — native Android input-method foundation.**

GoreeCloud Keyboard is an original GoreeCloud-owned keyboard implementation. The current implemented platform is Android through `InputMethodService` and a first-party rendering/input surface. Apple-platform support remains product direction and is not claimed as currently implemented.

## Current implemented scope

- Native Android `InputMethodService` integration.
- First-party `KeyboardView` rendering and pointer-input handling.
- QWERTY letters with shift, backspace, space, and enter.
- First-party letters/symbols mode switching with `?123`, `ABC`, and `=\\<` controls.
- Primary symbol page with digits and common punctuation plus a secondary first-party page with brackets, operators, currency marks, and common typographic symbols.
- Local-only GoreeCloud Quill suggestion boundary with deterministic prefix suggestions and bounded typo-correction candidates.
- Sensitive-editor classification that clears composing context and suppresses suggestion collection, display, and acceptance for protected input variations.
- No Android network permission in the current application foundation.
- Glaze UI 2.2.0 Adoption Candidate source mapping for compatible spacing/radius geometry, 48 dp normal and 56 dp Touch Assistance interaction floors, canonical Light/Dark appearance values, and current Application/System Shell classification.
- Experimental Glaze Motion evaluation remains test-only and is not a production dependency.
- Android unit/build and emulator validation infrastructure, including native rendered secondary-symbol navigation and character hit testing.

## Native input behavior

### Letters layer

The default layer exposes QWERTY alphabetic rows. Shift affects alphabetic output only and automatically returns to the unshifted state after a shifted character is committed.

### Primary symbols layer

Tap `?123` from letters to open the primary symbol page. It exposes digits and common punctuation. `ABC` returns directly to letters, while `=\\<` opens the secondary symbol page.

### Secondary symbols layer

The secondary page exposes additional brackets, mathematical/operator punctuation, path/separator characters, currency marks, and common typographic symbols. `ABC` returns directly to letters and `?123` returns directly to the primary symbol page.

Entering any non-letter layer clears the current suggestion composition buffer. Non-letter symbol input does not become Quill suggestion context.

### Editor transitions

Starting or finishing an input view resets the keyboard to the letters layer, clears temporary composing context, resets shift, and re-evaluates the editor privacy classification.

## Privacy and security boundaries

### Privacy Shield / Privacy Center

Keyboard input is minimized by default. The current suggestion path is local-only, and sensitive-editor handling fails closed by suppressing suggestion capture and presentation. No production claim is made for future cloud-assisted input until an explicit Privacy Shield contract and acceptance evidence exist.

### Wardveil Security / Security Center

The application does not treat UI state as security authority. Future downloadable dictionaries, models, themes, clipboard integrations, voice/input adapters, or other external content require explicit Wardveil validation appropriate to the object and execution boundary before production use.

### Everkeep / Continuity Center

No keyboard history, learned dictionary, or synchronized personal-language corpus is currently claimed as an Everkeep-backed durable feature. Future persistence, backup, recovery, portability, or succession behavior requires an explicit continuity contract.

### GoreeCloud Identity / Identity Center

The current native keyboard foundation does not require a GoreeCloud account to type. Future account-backed personalization or synchronization must use GoreeCloud Identity for authentication/authorization and must not make credential material available to the input surface.

### GoreeCloud Mesh / Mesh Center

Future cross-application capabilities must use governed GoreeCloud integration contracts rather than hidden application coupling. Current local typing does not depend on Mesh availability.

### Glaze UI / Design Center

Glaze UI 2.2.0 Stable is the current production design-system authority. The repository currently has a bounded Adoption Candidate source mapping and does not claim complete rendered/native/accessibility/System Glaze/tablet/foldable/representative-device/Human Visual Excellence acceptance. Deep Dark, Reduced Motion/Transparency, Increased Contrast, Forced Colors/native equivalents, 200% text/reflow, runtime Touch Assistance resolution, RTL/localization, TalkBack/Switch Access, production packaging, release, and Stable qualification remain separate gates.

Local emoji search remains an application-local input-navigation capability and is not GoreeCloud Universal Search. Glaze UI presentation semantics do not grant Universal Search, Control Center, security, privacy, identity, recovery, or other platform authority to Keyboard.

## Target capability families — not yet complete

The product direction includes, subject to separate implementation and acceptance:

- gesture/swipe typing;
- richer local correction and prediction;
- user and language dictionaries;
- multilingual layouts and language switching;
- emoji and further specialized input surfaces beyond the current two symbol pages;
- clipboard tools with explicit privacy controls;
- optional voice/input adapters where platform policy and privacy contracts permit;
- one-handed, split, tablet, foldable, and other adaptive layouts;
- accessibility improvements including TalkBack, switch access, large text, contrast, reduced-transparency, and representative device acceptance;
- GoreeCloud Quill writing assistance through privacy-preserving boundaries;
- user-controlled personalization, portability, backup, and synchronization where explicitly implemented.

These targets are product scope, not current implementation claims.

## Production and Stable acceptance gates

Development source or passing CI is not equivalent to production acceptance or Stable qualification. Production promotion requires evidence appropriate to the shipped platform, including native runtime behavior, privacy/security boundaries, Glaze UI conformance, accessibility, representative devices/form factors, performance and power behavior, signed packaging, release/distribution controls, rollback/recovery, and any applicable GoreeCloud platform-system acceptance.