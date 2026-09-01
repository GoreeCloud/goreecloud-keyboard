#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
DOC = ROOT / "docs/glaze-motion-evaluation.md"
ADOPTION = ROOT / "docs/glaze-ui-adoption.md"
TEST = ROOT / "android/app/src/androidTest/kotlin/com/goreecloud/keyboard/GlazeMotionExperimentalKeyboardRuntimeTest.kt"
MAIN = ROOT / "android/app/src/main"
KEYBOARD_VIEW = MAIN / "kotlin/com/goreecloud/keyboard/KeyboardView.kt"
TOKENS = MAIN / "kotlin/com/goreecloud/keyboard/GlazeKeyboardTokens.kt"
REFERENCE_REVISION = "b386c793c047e2f5d5d92125732f142e7fdf32dc"
STABLE_REVISION = "c49113eb8b93c267613fdf1bbca1f814495acad7"
MARKER = "GlazeMotionExperimental"


def fail(message: str) -> None:
    raise SystemExit(f"Glaze Motion keyboard evaluation failed: {message}")


def main() -> None:
    for path in (DOC, ADOPTION, TEST, KEYBOARD_VIEW, TOKENS):
        if not path.is_file():
            fail(f"missing required evidence: {path.relative_to(ROOT)}")

    doc_text = DOC.read_text(encoding="utf-8")
    adoption_text = ADOPTION.read_text(encoding="utf-8")
    test_text = TEST.read_text(encoding="utf-8")
    view_text = KEYBOARD_VIEW.read_text(encoding="utf-8")
    token_text = TOKENS.read_text(encoding="utf-8")

    required_doc = [
        "Lifecycle: **Experimental 0.5**",
        f"Reviewed canonical revision: `{REFERENCE_REVISION}`",
        "Runtime compatibility baseline: **0.4.0**",
        "Evaluation mode: **native Android interaction mapping, test-only**",
        "Production dependency: **no**",
        "Glaze UI 2.1 Stable is the production design-system authority.",
        "insufficient for Candidate promotion by itself",
    ]
    for evidence in required_doc:
        if evidence not in doc_text:
            fail(f"missing lifecycle or evidence boundary `{evidence}`")

    required_adoption = [
        "# Glaze UI 2.1 Adoption Candidate — GoreeCloud Keyboard",
        "Status: **Adoption Candidate**",
        "Required Stable baseline: **Glaze UI 2.1.0**",
        f"Reviewed canonical Stable revision: `{STABLE_REVISION}`",
        "Reviewed Stable tag: `v2.1.0`",
        "Production eligible on the Glaze UI gate: **no**",
        "48 dp general interaction floor",
        "56 dp Touch Assistance interaction floor",
        "14 dp `radius.md` token",
        "Dark maps the canonical canvas",
        "Deep Dark remains unimplemented",
        "Glaze UI 2.2 remains non-consumer-eligible",
        "Glaze UI 2.1 Stable is the production design-system authority.",
        "Experimental Glaze Motion remains test-only and is not a production dependency.",
    ]
    for evidence in required_adoption:
        if evidence not in adoption_text:
            fail(f"missing Glaze UI adoption boundary `{evidence}`")

    for stale in (
        "Required Stable baseline: **Glaze UI 2.0.0**",
        "Glaze UI 2.0 Stable is the production design-system authority.",
        "Glaze UI 2.0 Stable remains the production design-system authority.",
        "Required Stable baseline: **Glaze UI 1.6.0**",
        "Glaze UI 1.6 Stable remains the production design-system authority.",
        "Required Stable baseline: **Glaze UI 1.5.0**",
        "Glaze UI 1.5 Stable remains the production design-system authority.",
        "12 dp medium utility radius",
    ):
        if stale in adoption_text or stale in doc_text:
            fail(f"stale Stable adoption boundary remains active: `{stale}`")

    required_tokens = [
        "current Glaze UI 2.1 Stable token map",
        "enum class Appearance { LIGHT, DARK }",
        "const val Space1Dp = 4f",
        "const val Space2Dp = 8f",
        "const val RadiusMediumDp = 14f",
        "const val GeneralInteractionFloorDp = 48f",
        "const val TouchAssistanceInteractionFloorDp = 56f",
        "const val SuggestionStripHeightDp = GeneralInteractionFloorDp",
        "fun interactionFloorDp(touchAssistance: Boolean): Float =",
        "0xFFEEF3F9",
        "0xC2FFFFFF",
        "0xFF172033",
        "0xFF67748A",
        "0xFF0D1119",
        "0xC719202D",
        "0xFFF3F6FB",
        "0xFFA1AEC0",
    ]
    for evidence in required_tokens:
        if evidence not in token_text:
            fail(f"missing Glaze UI 2.1 source mapping `{evidence}`")

    required_test = [
        f'const val REFERENCE_REVISION = "{REFERENCE_REVISION}"',
        'const val VERSION = "0.5.0"',
        'const val RUNTIME_BASELINE = "0.4.0"',
        "Settings.Global.ANIMATOR_DURATION_SCALE",
        "allowsOptionalSettling(",
        "KeyboardView(context)",
        "dispatchTouchEvent(event)",
    ]
    for evidence in required_test:
        if evidence not in test_text:
            fail(f"missing native test-only evidence `{evidence}`")

    required_consumer = [
        "class KeyboardView",
        "Configuration.UI_MODE_NIGHT_MASK",
        "GlazeKeyboardTokens.Appearance.DARK",
        "GlazeKeyboardTokens.Appearance.LIGHT",
        "GlazeKeyboardTokens.palette(appearance)",
        "GlazeKeyboardTokens.SuggestionStripHeightDp",
        "GlazeKeyboardTokens.RadiusMediumDp",
        "override fun onTouchEvent(event: MotionEvent)",
        "listener?.onSuggestion(hit.value)",
        "Action.TEXT -> {",
        "emojiRecents.record(hit.key.label)",
        "listener?.onText(hit.key.label)",
        "performClick()",
    ]
    for evidence in required_consumer:
        if evidence not in view_text:
            fail(f"representative Keyboard interaction surface no longer exposes `{evidence}`")

    production_hits = []
    for path in MAIN.rglob("*.kt"):
        if MARKER in path.read_text(encoding="utf-8"):
            production_hits.append(str(path.relative_to(ROOT)))
    if production_hits:
        fail(
            "Experimental mapping escaped test quarantine into production source: "
            + ", ".join(production_hits)
        )

    print(
        "Glaze UI 2.1 Keyboard Adoption Candidate + Glaze Motion 0.5 test-only boundary passed: "
        "current Stable source mapping validated and Experimental Motion remains quarantined."
    )


if __name__ == "__main__":
    main()
