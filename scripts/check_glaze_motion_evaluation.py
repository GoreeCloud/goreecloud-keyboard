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
OPTICS = MAIN / "kotlin/com/goreecloud/keyboard/GlazeKeyboardOptics.kt"
ATMOSPHERE = MAIN / "kotlin/com/goreecloud/keyboard/GlazeKeyboardAtmosphere.kt"
OPTICS_TEST = ROOT / "android/app/src/test/kotlin/com/goreecloud/keyboard/GlazeKeyboardOpticsTest.kt"
TOKENS_TEST = ROOT / "android/app/src/test/kotlin/com/goreecloud/keyboard/GlazeKeyboardTokensTest.kt"
MOTION_REFERENCE_REVISION = "b386c793c047e2f5d5d92125732f142e7fdf32dc"
GLAZE_VERSION = "1.4.0"
GLAZE_SOURCE_REVISION = "84cb3db4884042f0fa25ed6d475a127fb110f596"
MARKER = "GlazeMotionExperimental"


def fail(message: str) -> None:
    raise SystemExit("Keyboard GLAZE UI V1.4 / Motion boundary failed: " + message)


def require_all(label: str, text: str, markers: tuple[str, ...]) -> None:
    for marker in markers:
        if marker not in text:
            fail(f"{label} missing `{marker}`")


def main() -> None:
    for path in (
        DOC,
        ADOPTION,
        PLATFORM,
        TEST,
        KEYBOARD_VIEW,
        TOKENS,
        OPTICS,
        ATMOSPHERE,
        OPTICS_TEST,
        TOKENS_TEST,
    ):
        if not path.is_file():
            fail(f"missing required evidence: {path.relative_to(ROOT)}")

    doc_text = DOC.read_text(encoding="utf-8")
    adoption_text = ADOPTION.read_text(encoding="utf-8")
    platform_text = PLATFORM.read_text(encoding="utf-8")
    test_text = TEST.read_text(encoding="utf-8")
    view_text = KEYBOARD_VIEW.read_text(encoding="utf-8")
    token_text = TOKENS.read_text(encoding="utf-8")
    optics_text = OPTICS.read_text(encoding="utf-8")
    atmosphere_text = ATMOSPHERE.read_text(encoding="utf-8")
    optics_test_text = OPTICS_TEST.read_text(encoding="utf-8")
    tokens_test_text = TOKENS_TEST.read_text(encoding="utf-8")

    require_all(
        "Motion boundary",
        doc_text,
        (
            "Lifecycle: **Experimental 0.5**",
            f"Reviewed canonical revision: `{MOTION_REFERENCE_REVISION}`",
            "Runtime compatibility baseline: **0.4.0**",
            "Evaluation mode: **native Android interaction mapping, test-only**",
            "Production dependency: **no**",
            f"GLAZE UI V1.4 / `{GLAZE_VERSION}`",
            f"`{GLAZE_SOURCE_REVISION}`",
            "Motion remains separately Experimental",
            "insufficient for promotion by itself",
        ),
    )

    require_all(
        "Glaze adoption record",
        adoption_text,
        (
            "# GLAZE UI V1.4 Development Mapping — GoreeCloud Keyboard",
            "Status: **Migration in progress / Development**",
            f"Current Stable target: **GLAZE UI V1.4 (`{GLAZE_VERSION}`)**",
            f"Exact Stable merged source authority: `{GLAZE_SOURCE_REVISION}`",
            "Production eligible on the Glaze UI gate: **no**",
            "Neutral glass is the material. Color is an accent.",
            "Environmental Color Memory influence is intentionally **0%**",
            "Editor text, composing text, surrounding text, suggestion content, clipboard state, application identity",
            "V1.4.1 human-validation boundary",
            "Source/build/emulator success remains Development evidence only",
        ),
    )

    require_all(
        "V1.4 source token mapping",
        token_text,
        (
            f'const val TargetVersion = "{GLAZE_VERSION}"',
            f'const val SourceRevision = "{GLAZE_SOURCE_REVISION}"',
            "enum class Appearance { LIGHT, DARK, DEEP_DARK }",
            "const val GeneralInteractionFloorDp = 48f",
            "const val TouchAssistanceInteractionFloorDp = 56f",
            "const val PressedOverlayOpacity = 0.095f",
            "const val FocusWidthDp = 3f",
            "const val IncreasedContrastFocusWidthDp = 4f",
            "Appearance.DEEP_DARK -> DeepDarkPalette",
            "fun stateOverlayArgb(",
            "Typed/editor content, suggestions",
        ),
    )

    require_all(
        "V1.4 Keyboard optical policy",
        optics_text,
        (
            f'const val TargetVersion = "{GLAZE_VERSION}"',
            f'const val StableSourceRevision = "{GLAZE_SOURCE_REVISION}"',
            "const val MaxEnvironmentalColorMemoryInfluence = 0f",
            "const val EditorContentMayDriveOptics = false",
            "const val ClipboardMayDriveOptics = false",
            "const val SuggestionContentMayDriveOptics = false",
            "const val AppIdentityMayDriveOptics = false",
            "const val RemoteContextAllowed = false",
            "const val TelemetryRequired = false",
            "SOLID_ACCESSIBLE",
            "semanticProtection = 1f",
            "decorativeTintAllowed = false",
            "environmentalColorMemoryInfluence = 0f",
        ),
    )

    require_all(
        "V1.4 atmosphere/material boundary",
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
            "const val EnvironmentalAuraOptional = false",
            "const val EnvironmentalAuraMayPassThroughBackdrop = false",
            "const val EnvironmentalColorMemoryEnabled = false",
            "const val EnvironmentalColorMemoryMaxInfluence = 0f",
            "const val EditorContentSamplingAllowed = false",
            "const val SuggestionContentSamplingAllowed = false",
            "const val ClipboardSamplingAllowed = false",
            "const val ApplicationIdentitySamplingAllowed = false",
            "const val RemoteColorDerivationAllowed = false",
            "const val PersistentSampleHistoryAllowed = false",
            "const val SemanticInferenceAllowed = false",
            "const val AnimatedAtmosphereEnabled = false",
        ),
    )

    require_all(
        "V1.4 optical tests",
        optics_test_text,
        (
            "ordinaryKeyboardOpticsRemainNeutralAndSemanticFirst",
            "increasedContrastRaisesFrostWithoutEnablingDecoration",
            "reducedTransparencyFailsClosedToSolidAccessible",
            "forcedColorsFailsClosedToSolidAccessible",
            "sensitiveInputSourcesCanNeverDriveOptics",
        ),
    )

    require_all(
        "V1.4 token tests",
        tokens_test_text,
        (
            "currentMappingPinsExactGlazeUiV14StableAuthority",
            "v14AtmosphereCannotTintSubstrateOrEnableSensitiveObservation",
            f'assertEquals("{GLAZE_VERSION}", GlazeKeyboardTokens.TargetVersion)',
            f'"{GLAZE_SOURCE_REVISION}"',
        ),
    )

    require_all(
        "Platform Contract v0.3",
        platform_text,
        (
            'schema_version: "0.3"',
            "  id: goreecloud-keyboard",
            f'  glaze_ui:\n    result: applicable-migration-required\n    version: "{GLAZE_VERSION}"',
            '  platform_contract: "0.3"',
            f'  glaze_ui_required: "{GLAZE_VERSION}"',
            "goreecloud-platform-contract==0.3",
            f"glaze-ui=={GLAZE_VERSION}",
            "GlazeKeyboardOptics.kt",
            "GlazeKeyboardOpticsTest.kt",
            "GoreeCloud Sync change tracking, authorized replication, conflict reconciliation",
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
        fail("KeyboardView must not infer or auto-select Deep Dark until a governed runtime policy exists")
    if "GlazeKeyboardAtmosphere" in view_text or "GlazeKeyboardOptics" in view_text:
        fail("KeyboardView must not gain implicit optical/environment observation in this migration slice")

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

    # Sensitive-input visual policy stays non-collecting. Keep direct collection/
    # network primitives out of the optical and atmosphere files entirely.
    optical_authority = optics_text + "\n" + atmosphere_text
    for forbidden in (
        "InputConnection",
        "ClipboardManager",
        "EditorInfo",
        "getTextBeforeCursor",
        "getTextAfterCursor",
        "getSelectedText",
        "HttpURLConnection",
        "URLConnection",
        "Socket(",
        "WallpaperManager",
        "Camera",
    ):
        if forbidden in optical_authority:
            fail(f"sensitive optical boundary contains forbidden observation/network primitive `{forbidden}`")

    production_hits = []
    for path in MAIN.rglob("*.kt"):
        if MARKER in path.read_text(encoding="utf-8"):
            production_hits.append(str(path.relative_to(ROOT)))
    if production_hits:
        fail(
            "Experimental Motion escaped test quarantine into production source: "
            + ", ".join(production_hits)
        )

    active_records = adoption_text + "\n" + doc_text + "\n" + platform_text + "\n" + token_text
    for stale in (
        "Repository-local source target: **GLAZE UI V1.2 (`1.2.0`)**",
        "Governed Stable consumer baseline: **GLAZE UI V1.1 (`1.1.0`)**",
        'schema_version: "0.2"',
        '  platform_contract: "0.2"',
        "goreecloud-platform-contract==0.2",
        '  glaze_ui_required: "1.1.0"',
        "glaze-ui==1.1.0",
        '  glaze_ui:\n    result: applicable-migration-required\n    version: "1.2.0"',
        "known immutable import-closure defect",
        "Glaze UI 2.2.0 Stable is the production design-system authority.",
        "stable_eligible: true",
    ):
        if stale in active_records:
            fail(f"active evidence retains stale/superseded Glaze authority `{stale}`")

    print(
        "Keyboard GLAZE UI V1.4 boundary passed: "
        f"target {GLAZE_VERSION} at {GLAZE_SOURCE_REVISION}; Platform Contract 0.3 remains "
        "migration-required/nonconformant; GoreeCloud Sync remains separately blocked; "
        "Android runtime remains Light/Dark only; sensitive content cannot drive optics; "
        "Environmental Color Memory remains 0%; Experimental Motion remains quarantined; "
        "application acceptance stays separate."
    )


if __name__ == "__main__":
    main()
