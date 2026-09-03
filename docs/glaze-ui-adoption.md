# GLAZE UI V1.0 Migration — GoreeCloud Keyboard

Status: **Migration in progress / Development**  
Official target: **GLAZE UI V1.0 (`1.0.0`)**  
Canonical repository: `GoreeCloud/goreecloud-glaze-ui`  
Exact V1 source authority: `70909bbdccad378fb7281ae1842e2f5beed64c38`  
Production eligible on the Glaze UI gate: **no**

## Scope

GLAZE UI V1.0 is the official and only current GoreeCloud design-system target. This repository-local migration maps the applicable post-reset foundation into GoreeCloud Keyboard's first-party Android surface. It does **not** establish complete V1 conformance, production acceptance, representative-device acceptance, signed release, or Stable qualification. No pre-reset Glaze acceptance is inherited as V1 evidence.

The current native surface remains first-party `KeyboardView`; no web runtime, remote UI layer, network permission, analytics, advertising, or Experimental Motion production dependency is introduced.

## Implemented V1 source mapping

- `GlazeKeyboardTokens.TargetVersion` is `1.0.0` and `SourceRevision` pins exact canonical revision `70909bbdccad378fb7281ae1842e2f5beed64c38`.
- The keyboard retains canonical 4 dp and 8 dp spacing steps it consumes.
- Existing `RadiusMediumDp` is retained as a source-compatible property name but now maps to the V1 12 dp small/control radius tier.
- The suggestion strip and ordinary key interaction floor remain 48 dp.
- The bounded token map exposes the 56 dp Touch Assistance / far-view interaction floor; platform-setting resolution and full assisted hit-area behavior remain application acceptance work.
- Light maps V1 canvas `#F5F7FA`, bounded interactive overlay surface, primary text `#151A23`, muted text `#5D6675`, and V1 line semantics.
- Dark maps V1 canvas `#0B0D11`, bounded interactive overlay surface, primary text `#F5F7FA`, muted text `#B0B7C3`, and V1 line semantics.
- `KeyboardView` continues to select Light/Dark from Android night mode at draw time.
- V1 publishes Deep Dark, but Keyboard does not yet implement it; it remains an explicit implementation and acceptance gap rather than being approximated from pre-reset values.
- Keyboard is an **Application** surface under the V1 System Shell contract. Long-press alternates are transient application interaction, not Control Center or Universal Search.
- Local emoji search remains application-local input navigation and is not GoreeCloud Universal Search.
- Existing Quill suggestions, sensitive-editor gating, typo correction, emoji, alternate-character, deletion, and key-release semantics remain first-party and on-device.

## V1 presentation boundary

Keyboard follows the V1 presentation rule: **Solid where users read or make explicit critical decisions. Glazed where users interact with transient navigation, command, search, control, or feedback chrome.**

Ordinary key/suggestion content must remain readable and state must not depend on translucency. Any transient Glaze treatment must fail to a solid equivalent without removing controls or changing input authority.

Glaze presentation cannot grant security, privacy, Identity, Mesh, Everkeep, Universal Search, Control Center, clipboard, editor-observation, learning, network, or other authority.

## Repository-local evidence

- `android/app/src/main/kotlin/com/goreecloud/keyboard/KeyboardView.kt` is the first-party rendering/pointer-input surface.
- `android/app/src/main/kotlin/com/goreecloud/keyboard/GlazeKeyboardTokens.kt` is the bounded V1 token mapping.
- `android/app/src/main/kotlin/com/goreecloud/keyboard/AlternatePopupLayout.kt` is the viewport-bounded geometry and hit-test authority for long-press alternates.
- `GlazeKeyboardTokensTest` locks V1 provenance, geometry, Light/Dark values, and 48/56 dp target floors.
- `AlternatePopupLayoutTest` locks normal, edge, compact multi-row, failure, gap/unused-cell, outside-point, and non-finite hit-test behavior.
- The Android manifest requests no network permission.
- Android CI runs the repository-local GLAZE UI / Glaze Motion governance check, JVM unit tests, debug assembly, and emulator interaction job.

## Accessibility and adaptive work still required

This source migration does not establish:

- Deep Dark;
- complete V1 component/state and material-role mapping;
- Reduced Transparency / solid fallback behavior;
- Increased Contrast and forced-colors/native equivalents;
- Reduced Motion;
- 200% large-text/reflow behavior within host IME constraints;
- platform Touch Assistance detection and 56 dp assisted geometry;
- RTL/localization expansion;
- TalkBack/Switch Access acceptance;
- representative phone/tablet/foldable and host-IME adaptation;
- representative physical-device long-press/slide/release ergonomics;
- performance/power fallback acceptance; or
- Human Visual Excellence acceptance.

Source/build/emulator success remains Development evidence only until applicable runtime and release gates are satisfied.

## Glaze Motion boundary

Historical Glaze Motion 0.5 evaluation remains test-only. Glaze Motion is separately governed Experimental work unless explicitly incorporated into a future V1.x contract. It is not a production dependency and cannot establish V1 consumer acceptance.

## Historical evidence boundary

Pre-reset Glaze UI 2.x/earlier commits, pull requests, CI runs, and discussion remain immutable Git/changelog audit history only. They may explain implementation ancestry but do not define the current target and do not satisfy V1 acceptance.

## Acceptance still required

- Fresh exact-head governance, unit, build, and emulator CI for this V1 migration.
- Complete applicable V1 component/state and fallback mapping.
- Deep Dark and required accessibility/resilience modes.
- Representative-device and form-factor ergonomics.
- TalkBack/Switch Access and focus/announcement behavior where applicable to the IME surface.
- Privacy Shield and Wardveil Security acceptance appropriate to sensitive input processing.
- Everkeep acceptance for any approved durable-state recovery scope.
- Mesh/Identity integration only where applicable and authorized.
- Production signing, distribution, release approval, and Stable qualification.

## Rollback

If the V1 mapping causes a regression, revert the exact Keyboard migration commit or merge to the prior accepted Keyboard revision. Do not weaken the canonical V1 contract, and do not use a retired Glaze product version as the current rollback target.
