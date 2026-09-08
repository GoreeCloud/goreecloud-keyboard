#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
DOC = ROOT / "docs/glaze-motion-evaluation.md"
ADOPTION = ROOT / "docs/glaze-ui-adoption.md"
PLATFORM = ROOT / "goreecloud.platform.yaml"
TEST = ROOT / "android/app/src/androidTest/kotlin/com/goreecloud/keyboard/GlazeMotionExperimentalKeyboardRuntimeTest.kt"
MAIN = ROOT / "android/app/src/main"
KEYBOARD_VIEW = MAIN / "kotlin/com/goreecloud/keyboard/KeyboardView.kt"
TOKENS = MAIN / "kotlin/com/goreecloud/keyboard/GlazeKeyboardTokens.kt"
ATMOSPHERE = MAIN / "kotlin/com/goreecloud/keyboard/GlazeKeyboardAtmosphere.kt"
MOTION_REFERENCE_REVISION = "b386c793c047e2f5d5d92125732f142e7fdf32dc"
STABLE_GLAZE_VERSION = "1.2.0"
GLAZE_SOURCE_REVISION = "f285b9145e27e6e7027b075c37299d101945c272"
MARKER = "GlazeMotionExperimental"


def fail(message: str) -> None:
    raise SystemExit("Keyboard Glaze V1.2 / Experimental Motion boundary failed: " + message)


def require_all(label: str, text: str, markers: tuple[str, ...]) -> None:
    for marker in markers:
        if marker not in text:
            fail(f"{label} missing `{marker}`")


def main() -> None:
    for path in (DOC, ADOPTION, PLATFORM, TEST, KEYBOARD_VIEW, TOKENS, ATMOSPHERE):
        if not path.is_file():
            fail(f"missing required evidence: {path.relative_to(ROOT)}")

    doc_text = DOC.read_text(encoding="utf-8")
    adoption_text = ADOPTION.read_text(encoding="utf-8")
    platform_text = PLATFORM.read_text(encoding="utf-8")
    test_text = TEST.read_text(encoding="utf-8")
    view_text = KEYBOARD_VIEW.read_text(encoding="utf-8")
    token_text = TOKENS.read_text(encoding="utf-8")
    atmosphere_text = ATMOSPHERE.read_text(encoding="utf-8")

    require_all(
        "Motion boundary",
        doc_text,
        (
            "Lifecycle: **Experimental 0.5**",
            f"Reviewed canonical revision: `{MOTION_REFERENCE_REVISION}`",
            "Runtime compatibility baseline: **0.4.0**",
            "Evaluation mode: **native Android interaction mapping, test-only**",
            "Production dependency: **no**",
            f"GLAZE UI V1.2 / `{STABLE_GLAZE_VERSION}` Stable",
            f"`{GLAZE_SOURCE_REVISION}`",
            "Motion 0.5 is still Experimental/test-only",
            "insufficient for promotion by itself",
        ),
    )

    require_all(
        "Glaze adoption record",
        adoption_text,
        (
            "# GLAZE UI V1.2 Stable Adoption — GoreeCloud Keyboard",
            f"Current design-system target: **GLAZE UI V1.2 (`{STABLE_GLAZE_VERSION}`) Stable**",
            f"Exact Stable promotion revision: `{GLAZE_SOURCE_REVISION}`",
            "Production eligible on the Glaze UI gate: **no**",
            "Neutral glass is the material. Color is an accent.",
            "Deep Dark source material is explicitly defined",
            "no-personalized-learning",
            "one-field `goreecloud-keyboard-preferences/1` portability boundary",
            "Glaze Motion 0.5 evaluation remains test-only",
        ),
    )

    require_all(
        "V1.2 Stable token mapping",
        token_text,
        (
            f'const val TargetVersion = "{STABLE_GLAZE_VERSION}"',
            f'const val SourceRevision = "{GLAZE_SOURCE_REVISION}"',
            "enum class Appearance { LIGHT, DARK, DEEP_DARK }",
            "const val GeneralInteractionFloorDp = 48f",
            "const val TouchAssistanceInteractionFloorDp = 56f",
            "const val PressedOverlayOpacity = 0.095f",
            "const val FocusWidthDp = 3f",
            "const val IncreasedContrastFocusWidthDp = 4f",
            "Appearance.DEEP_DARK -> DeepDarkPalette",
            "fun stateOverlayArgb(",
        ),
    )

    require_all(
        "V1.2 neutral material boundary",
        atmosphere_text,
        (
            "Neutral glass is the material. Color is an accent.",
            "const val DefaultMaterialTintContribution = 0f",
            "const val TealAsBaseMaterialAllowed = false",
            "const val GreenAsBaseMaterialAllowed = false",
            "const val AquaAsBaseMaterialAllowed = false",
            "const val AmberAsBaseMaterialAllowed = false",
            "const val BrandColorMayDefineSubstrate = false",
            "const val SemanticColorMayDefineSubstrate = false",
            "const val EnvironmentalColorMemoryEnabled = false",
            "const val RemoteColorDerivationAllowed = false",
            "const val PersistentSampleHistoryAllowed = false",
            "const val SemanticInferenceAllowed = false",
            "const val AnimatedAtmosphereEnabled = false",
            "No editor/content",
        ),
    )

    require_all(
        "Platform Contract v0.2",
        platform_text,
        (
            'schema_version: "0.2"',
            "  id: goreecloud-keyboard",
            f'  glaze_ui:\n    result: applicable-migration-required\n    version: "{STABLE_GLAZE_VERSION}"',
            f'  glaze_ui_required: "{STABLE_GLAZE_VERSION}"',
            "goreecloud-platform-contract==0.2",
            f"glaze-ui=={STABLE_GLAZE_VERSION}",
            "conformance:\n  status: nonconformant",
        ),
    )

    require_all(
        "representative Keyboard runtime",
        view_text,
        (
            "class KeyboardView",
            "Configuration.UI_MODE_NIGHT_MASK",
            "GlazeKeyboardTokens.Appearance.DARK",
            "GlazeKeyboardTokens.Appearance.LIGHT",
            "GlazeKeyboardTokens.palette(appearance)",
            "private val pressedKeyPaint = Paint(Paint.ANTI_ALIAS_FLAG)",
            "private var pressedKeyBounds: RectF? = null",
            "GlazeKeyboardTokens.stateOverlayArgb(",
            "GlazeKeyboardTokens.PressedOverlayOpacity",
            "override fun onTouchEvent(event: MotionEvent)",
            "performClick()",
        ),
    )

    if "Appearance.DEEP_DARK" in view_text:
        fail("KeyboardView must not silently infer or auto-select Deep Dark")
    if "GlazeKeyboardAtmosphere" in view_text:
        fail("KeyboardView must not render optional atmosphere without separate acceptance")

    require_all(
        "native test-only Motion evidence",
        test_text,
        (
            f'const val REFERENCE_REVISION = "{MOTION_REFERENCE_REVISION}"',
            'const val VERSION = "0.5.0"',
            'const val RUNTIME_BASELINE = "0.4.0"',
            "Settings.Global.ANIMATOR_DURATION_SCALE",
            "allowsOptionalSettling(",
            "KeyboardView(context)",
            "dispatchTouchEvent(event)",
        ),
    )

    production_hits = []
    for path in MAIN.rglob("*.kt"):
        if MARKER in path.read_text(encoding="utf-8"):
            production_hits.append(str(path.relative_to(ROOT)))
    if production_hits:
        fail("Experimental Motion escaped test quarantine: " + ", ".join(production_hits))

    active_records = adoption_text + "\n" + doc_text + "\n" + platform_text
    stale_authority_claims = (
        'Governed Stable consumer baseline: **GLAZE UI V1.1 (`1.1.0`)**',
        '  glaze_ui_required: "1.1.0"',
        "glaze-ui==1.1.0",
        "known immutable import-closure defect",
        "Required Stable baseline: **Glaze UI 2.2.0**",
        "stable_eligible: true",
    )
    for stale in stale_authority_claims:
        if stale in active_records:
            fail(f"active evidence retains superseded authority claim `{stale}`")

    print(
        "Keyboard Glaze boundary passed: current Stable target "
        f"{STABLE_GLAZE_VERSION} at {GLAZE_SOURCE_REVISION}; Platform Contract remains "
        "migration-required/nonconformant pending application acceptance; Android runtime remains "
        "Light/Dark only; Experimental Motion remains quarantined."
    )


if __name__ == "__main__":
    main()
