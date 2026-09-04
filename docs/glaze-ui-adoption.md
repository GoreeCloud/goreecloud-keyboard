# GLAZE UI V1.1 Migration — GoreeCloud Keyboard

Status: **Migration in progress / Development**  
Official target: **GLAZE UI V1.1 (`1.1.0`)**  
Canonical repository: `GoreeCloud/goreecloud-glaze-ui`  
Exact Stable source authority: `15cc76d2bcd4065552dc31c77145b63f34d9e7b2`  
Production eligible on the Glaze UI gate: **no**

## Scope

GLAZE UI V1.1 / `1.1.0` is the current Stable GoreeCloud design-system target. This repository-local migration maps the applicable V1.1 foundation and optical contract into GoreeCloud Keyboard's first-party Android surface. It does **not** establish complete V1.1 consumer conformance, production acceptance, representative-device acceptance, signed release, or Stable qualification. Earlier V1.0 and pre-reset Glaze evidence remain historical migration/rollback evidence only.

The native surface remains first-party `KeyboardView`; no web runtime, remote UI layer, network permission, analytics, advertising, or Experimental Motion production dependency is introduced.

## Implemented V1.1 source mapping

- `GlazeKeyboardTokens.TargetVersion` is `1.1.0` and `SourceRevision` pins Stable release commit `15cc76d2bcd4065552dc31c77145b63f34d9e7b2`.
- Inherited V1 spacing consumed by the keyboard remains 4 dp and 8 dp.
- Existing `RadiusMediumDp` remains a source-compatible alias for the inherited V1 12 dp small/control structural radius.
- The suggestion strip and ordinary interaction floor remain 48 dp; the bounded map retains the 56 dp Touch Assistance / far-view floor without claiming platform preference resolution.
- V1.1 optical geometry references 8/16/24/32 dp plus capsule are recorded separately from structural radius and hit-target authority.
- Light preserves the inherited V1 structural canvas/text/line mapping.
- Dark preserves the inherited V1 structural canvas/text/line mapping.
- Deep Dark is now explicitly defined from the V1.1 structural appearance contract: canvas `#05070A`, bounded overlay surface derived from `#12161D`, primary text `#F5F7FA`, muted text `#ABB4C2`, and the inherited line family.
- `KeyboardView` continues to select only Light/Dark from Android night mode at draw time. Ordinary Android dark mode is not silently treated as Deep Dark. No new user appearance preference is added by this slice.
- `GlazeKeyboardAtmosphere` records the V1.1 Deep Teal + Soft Amber atmospheric primitive/cap subset separately from semantic and input authority.
- Atmosphere is **not rendered by KeyboardView** in this candidate. Environmental Color Memory, editor/content sampling, remote color derivation, persistent sample history, semantic inference, and animated atmosphere remain disabled/not authorized.
- Keyboard remains an **Application** surface. Long-press alternates and local emoji search remain local input interactions, not Control Center or Universal Search.
- Existing Quill suggestions, sensitive-editor gating, typo correction, emoji, alternate-character, deletion, and key-release semantics remain first-party and on-device.

## V1.1 authority and presentation boundary

Keyboard follows the inherited rule: **Solid where users read or make explicit critical decisions. Glazed where users interact with transient navigation, command, search, control, or feedback chrome.** For an IME, key labels, suggestion content, selection/focus indication, and sensitive-input behavior are higher priority than atmosphere.

The V1.1 authority order remains controlling: producer-authoritative protected meaning and accessibility resolution precede optical atmosphere. Removing atmosphere must never remove content, actions, focus, semantic state, or hierarchy.

Deep Teal or Soft Amber cannot mean privacy, security, protection, identity, recovery, synchronization, availability, sensitive-editor state, focus, or selection. Glaze presentation grants no clipboard, editor-observation, learning, network, Identity, Mesh, Everkeep, Privacy Shield, or Wardveil Security authority.

## Privacy boundary

The V1.1 migration does not add any observation path. In particular it adds no:

- typed/composing/surrounding text read;
- suggestion or learned-input persistence;
- emoji-recents or emoji-search export;
- clipboard access;
- editor/content sampling for color;
- telemetry or analytics;
- remote design or color derivation;
- network permission;
- Identity or Mesh session; or
- background synchronization.

The existing one-field `goreecloud-keyboard-preferences/1` portability boundary remains unchanged and still contains only the explicitly selected emoji category.

## Repository-local evidence

- `android/app/src/main/kotlin/com/goreecloud/keyboard/KeyboardView.kt` — first-party rendering/pointer-input surface; retains Android Light/Dark runtime selection.
- `android/app/src/main/kotlin/com/goreecloud/keyboard/GlazeKeyboardTokens.kt` — bounded V1.1 structural/optical source mapping including explicit Deep Dark.
- `android/app/src/main/kotlin/com/goreecloud/keyboard/GlazeKeyboardAtmosphere.kt` — non-semantic V1.1 atmospheric source contract, currently not rendered.
- `android/app/src/main/kotlin/com/goreecloud/keyboard/AlternatePopupLayout.kt` — viewport-bounded long-press geometry/hit-test authority.
- `GlazeKeyboardTokensTest` — exact V1.1 provenance, inherited geometry, optical references, Light/Dark/Deep Dark values, interaction floors, and disabled atmosphere-observation assertions.
- `AlternatePopupLayoutTest` — normal, edge, compact multi-row, failure, gap/unused-cell, outside-point, and non-finite hit-test behavior.
- Android manifest — no network permission.
- Android CI — repository Glaze/Motion governance, JVM tests, debug assembly, and native emulator interaction validation.

## Acceptance still required

This source migration still does not establish:

- a reviewed runtime policy for selecting Deep Dark, if Keyboard should expose one;
- complete V1.1 component/state/material-role mapping;
- Reduced Transparency / solid fallback acceptance;
- Increased Contrast and forced-colors/native-equivalent acceptance;
- Reduced Motion;
- 200% large-text/reflow within host IME constraints;
- platform Touch Assistance detection and 56 dp assisted geometry;
- RTL/localization expansion;
- TalkBack/Switch Access acceptance;
- representative phone/tablet/foldable and host-IME adaptation;
- representative physical-device long-press/slide/release ergonomics;
- performance/power fallback acceptance;
- Human Visual Excellence review;
- Privacy Shield and Wardveil Security acceptance appropriate to sensitive input processing;
- Everkeep acceptance for any approved durable-state recovery scope;
- Mesh/Identity integration only where applicable and authorized; or
- production signing, distribution, release approval, and Stable qualification.

Source/build/emulator success remains Development evidence only until those applicable runtime and release gates are satisfied.

## Glaze Motion boundary

Historical Glaze Motion 0.5 evaluation remains test-only. Glaze Motion is separately governed Experimental work and is not promoted by V1.1. It is not a production dependency and cannot establish V1.1 consumer acceptance.

## Rollback

If this V1.1 source mapping causes a regression, revert the exact Keyboard V1.1 migration commit/merge to the prior validated Keyboard revision. Do not weaken the canonical V1.1 contract, and do not reactivate an older Glaze release as the current product target.
